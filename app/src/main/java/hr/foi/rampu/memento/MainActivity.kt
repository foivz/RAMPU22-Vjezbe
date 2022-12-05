package hr.foi.rampu.memento

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.wearable.Wearable
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.memento.adapters.MainPagerAdapter
import hr.foi.rampu.memento.database.TasksDatabase
import hr.foi.rampu.memento.fragments.CompletedFragment
import hr.foi.rampu.memento.fragments.NewsFragment
import hr.foi.rampu.memento.fragments.PendingFragment
import hr.foi.rampu.memento.sync.WearableSynchronizer
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var navDrawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    lateinit var onSharedPreferencesListener: OnSharedPreferenceChangeListener
    private val dataClient by lazy { Wearable.getDataClient(this) }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_LANG_CHANGED) {
                recreate()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!applyUserSettings()) {
            return
        }

        loadViews()

        val numberOfAddedFragments = addAllFragments()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            navView.menu.setGroupDividerEnabled(true)
        }

        addNavMenuItems(numberOfAddedFragments)

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                navView.menu.getItem(position).isChecked = true
            }
        })

        TasksDatabase.buildInstance(applicationContext)
        //MockDataLoader.loadMockData()
        syncTasks()
    }

    private fun applyUserSettings(): Boolean {
        PreferenceManager.getDefaultSharedPreferences(this)?.let { pref ->
            PreferencesActivity.switchDarkMode(pref.getBoolean("preference_dark_mode", false))
            val lang = pref.getString("preference_language", "EN")
            if (lang != null) {
                val locale = Locale(lang)
                if (resources.configuration.locales[0].language != locale.language) {
                    resources.configuration.setLocale(locale)
                    Locale.setDefault(locale)
                    createConfigurationContext(resources.configuration)
                    recreate()
                    return false
                }
            }
        }
        return true
    }

    private fun addAllFragments(): Int {
        MainPagerAdapter(supportFragmentManager, lifecycle).apply {
            addFragment(
                MainPagerAdapter.FragmentItem(
                    R.string.tasks_pending,
                    R.drawable.ic_baseline_assignment_late_24,
                    PendingFragment::class
                )
            )
            addFragment(
                MainPagerAdapter.FragmentItem(
                    R.string.tasks_completed,
                    R.drawable.ic_baseline_assignment_turned_in_24,
                    CompletedFragment::class
                )
            )
            addFragment(
                MainPagerAdapter.FragmentItem(
                    R.string.news,
                    R.drawable.ic_baseline_wysiwyg_24,
                    NewsFragment::class
                )
            )

            viewPager2.adapter = this

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.setText(fragmentItems[position].titleRes)
                tab.setIcon(fragmentItems[position].iconRes)
            }.attach()

            fragmentItems.withIndex().forEach { (index, fragmentItem) ->
                navView.menu
                    .add(0, index, index, fragmentItem.titleRes)
                    .setIcon(fragmentItem.iconRes)
                    .setCheckable(true)
                    .setChecked((index == 0))
                    .setOnMenuItemClickListener {
                        viewPager2.setCurrentItem(index, true)
                        navDrawerLayout.closeDrawers()
                        return@setOnMenuItemClickListener true
                    }
            }

            return fragmentItems.size
        }
    }

    private fun addNavMenuItems(numberOfAddedFragments: Int) {
        var newNavMenuIndex = numberOfAddedFragments

        navView.menu
            .add(1, newNavMenuIndex, newNavMenuIndex, "Sync Wear OS")
            .setIcon(R.drawable.ic_baseline_watch_24)
            .setOnMenuItemClickListener { syncTasks(); true }

        newNavMenuIndex++
        val tasksCounterItem = navView.menu
            .add(2, newNavMenuIndex, newNavMenuIndex, "")
            .setEnabled(false)

        attachMenuItemToTasksCreatedCount(tasksCounterItem)

        newNavMenuIndex++
        navView.menu
            .add(3, newNavMenuIndex, newNavMenuIndex, getString(R.string.settings_menu_item))
            .setIcon(R.drawable.ic_baseline_settings_24)
            .setOnMenuItemClickListener {
                settingsLauncher.launch(Intent(this, PreferencesActivity::class.java))
                navDrawerLayout.closeDrawers()
                return@setOnMenuItemClickListener true
            }
    }

    private fun loadViews() {
        tabLayout = findViewById(R.id.tabs)
        viewPager2 = findViewById(R.id.viewpager)
        navDrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
    }

    private fun attachMenuItemToTasksCreatedCount(tasksCounterItem: MenuItem) {
        val sharedPreferences = getSharedPreferences("tasks_preferences", Context.MODE_PRIVATE)
        onSharedPreferencesListener =
            OnSharedPreferenceChangeListener { _, key ->
                if (key == "tasks_created_counter") {
                    updateTasksCreatedCounter(tasksCounterItem, sharedPreferences)
                }
            }
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferencesListener)
        updateTasksCreatedCounter(tasksCounterItem, sharedPreferences)
    }

    private fun updateTasksCreatedCounter(tasksCounterItem: MenuItem, sharedPreferences: SharedPreferences) {
        val tasksCreated = sharedPreferences.getInt("tasks_created_counter", 0)
        tasksCounterItem.title = "Tasks created: $tasksCreated"
    }

    private fun syncTasks() {
        WearableSynchronizer.sendTasks(
            TasksDatabase
                .getInstance()
                .getTasksDao()
                .getAllTasks(false),
            dataClient
        )
    }
}