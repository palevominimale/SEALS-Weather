package app.seals.weather.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import app.seals.weather.R

class FragmentSettings : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}