package app.seals.weather.ui.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import app.seals.weather.R
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.bootstrap.CheckPermissions
import org.koin.android.ext.android.inject

class FragmentSettings : PreferenceFragmentCompat() {

    private val settingsRepository: SettingsRepositoryInterface by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val checkPermissions = CheckPermissions(
            context = requireContext(),
            activity = requireActivity(),
            settingsRepository = settingsRepository
        )
        Preference.OnPreferenceChangeListener { preference, newValue ->
            if(preference.key == "useLocation" && newValue == true) {
                checkPermissions.execute()
            }
            true
        }
    }
}