package app.seals.weather.app.location

import android.content.Context
import android.widget.Toast
import app.seals.weather.data.models.SettingsDataModel
import app.seals.weather.data.repos.SettingsRepository
import com.google.android.gms.location.FusedLocationProviderClient

class GetLocation(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val context: Context,
    private val settingsRepository: SettingsRepository) {

    fun execute() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            val s = settingsRepository.get()
            val dm = SettingsDataModel(
                latitude = it.latitude.toFloat(),
                longitude = it.longitude.toFloat(),
                forecastStep = s.forecastStep,
                forecastDepth = s.forecastDepth,
                forecastStepWidget = s.forecastStepWidget,
                hourOfInterest = s.hourOfInterest,
                isLocationAllowed = s.isLocationAllowed,
                useLocation = s.useLocation
            )
            settingsRepository.set(dm)
            Toast.makeText(context, "Location: ${dm.latitude} ${dm.longitude}", Toast.LENGTH_SHORT).show()
        }.addOnCanceledListener {
            Toast.makeText(context, "Location is cancelled!", Toast.LENGTH_SHORT).show()
        } .addOnFailureListener {
            Toast.makeText(context, "Location was failed!", Toast.LENGTH_SHORT).show()
        }
    }

}