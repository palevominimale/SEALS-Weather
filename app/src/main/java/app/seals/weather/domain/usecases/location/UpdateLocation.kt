package app.seals.weather.domain.usecases.location

import android.location.LocationManager
import android.util.Log
import app.seals.weather.data.repos.SettingsRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

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
