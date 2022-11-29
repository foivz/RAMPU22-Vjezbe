package hr.foi.rampu.memento

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import hr.foi.rampu.memento.fragments.MementoSettingsFragment

const val RESULT_LANG_CHANGED = AppCompatActivity.RESULT_FIRST_USER

class PreferencesActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_settings, MementoSettingsFragment())
            .commit()

        getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "preference_dark_mode" -> switchDarkMode(sharedPreferences?.getBoolean(key, false))
            "preference_language" -> notifyLanguageChangedAndClose()
        }
    }

    private fun notifyLanguageChangedAndClose() {
        setResult(RESULT_LANG_CHANGED)
        finish()
    }

    companion object {
        fun switchDarkMode(isDarkModeSelected: Boolean?) {
            if (isDarkModeSelected == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}
