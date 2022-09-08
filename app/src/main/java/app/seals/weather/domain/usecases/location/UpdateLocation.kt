package app.seals.weather.domain.usecases.location

import android.location.Location
import android.location.LocationManager
import app.seals.weather.data.repos.SettingsRepository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.runBlocking

class UpdateLocation(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val settingsRepository: SettingsRepository
    ) {

    fun execute() {
        if (settingsRepository.getLocationUsageStatus()) {
            runBlocking {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    val location = Location(LocationManager.GPS_PROVIDER).apply {
                        this.latitude = it.latitude
                        this.longitude = it.longitude
                    }
                    settingsRepository.setLocation(location)
                }
            }
        }
    }
}