package app.seals.weather.domain.usecases.location

import app.seals.weather.data.repos.SettingsRepository
import com.google.android.gms.location.FusedLocationProviderClient

class UpdateLocation(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val settingsRepository: SettingsRepository
    ) {

    fun setOnSuccessListener(function: () -> Unit) {
        if (settingsRepository.getLocationUsageStatus()) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                settingsRepository.setLocation(it)
                function()
            }
        }
    }
}

