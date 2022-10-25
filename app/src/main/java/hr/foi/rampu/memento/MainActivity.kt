package hr.foi.rampu.memento

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.memento.adapters.MainPagerAdapter
import hr.foi.rampu.memento.fragments.CompletedFragment
import hr.foi.rampu.memento.fragments.NewsFragment
import hr.foi.rampu.memento.fragments.PendingFragment

class MainActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2
    lateinit var navDrawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabs)
        viewPager2 = findViewById(R.id.viewpager)
        navDrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.tasks_pending,
                R.drawable.ic_baseline_assignment_late_24,
                PendingFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.tasks_completed,
                R.drawable.ic_baseline_assignment_turned_in_24,
                CompletedFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.news,
                R.drawable.ic_baseline_wysiwyg_24,
                NewsFragment::class
            )
        )

        viewPager2.adapter = mainPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.setText(mainPagerAdapter.fragmentItems[position].titleRes)
            tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
        }.attach()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.title) {
                getString(R.string.tasks_pending) -> viewPager2.setCurrentItem(0, true)
                getString(R.string.tasks_completed) -> viewPager2.setCurrentItem(1, true)
                getString(R.string.news) -> viewPager2.setCurrentItem(2, true)
            }
            navDrawerLayout.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
    }
}