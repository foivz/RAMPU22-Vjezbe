package hr.foi.rampu.memento

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.foi.rampu.memento.fragments.MementoSettingsFragment

class PreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_settings, MementoSettingsFragment())
            .commit()
    }
}
