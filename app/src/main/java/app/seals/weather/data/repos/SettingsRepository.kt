package app.seals.weather.data.repos

import android.content.Context
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.data.models.SettingsDataModel

private const val PREFS = "preferences"
private const val LAT = "lat"
private const val LON = "lon"
private const val F_STEP = "forecastStep"
private const val F_STEP_W = "forecastStepWidget"
private const val F_DEPTH = "forecastDepth"
private const val HOUR = "hourOfInterest"
private const val L_ALLOW = "isLocationAllowed"
private const val L_USE = "useLocation"

class SettingsRepository(context: Context) : SettingsRepositoryInterface {

    private val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    override fun get(): SettingsDataModel {
        return SettingsDataModel(
            latitude = prefs.getFloat(LAT, 46.4887F),
            longitude = prefs.getFloat(LON, 3.99390F),
            forecastStep = prefs.getInt(F_STEP, 1),
            forecastStepWidget = prefs.getInt(F_STEP_W, 1),
            forecastDepth = prefs.getInt(F_DEPTH, 7),
            hourOfInterest = prefs.getInt(HOUR, 15),
            isLocationAllowed = prefs.getBoolean(L_ALLOW, false),
            useLocation = prefs.getBoolean(L_USE, false)
        )
    }

    override fun set(settings: SettingsDataModel) {
        prefs.edit().apply {
            putFloat(LAT, settings.latitude)
            putFloat(LON, settings.longitude)
            putInt(F_STEP, settings.forecastStep)
            putInt(F_STEP_W, settings.forecastStepWidget)
            putInt(F_DEPTH, settings.forecastDepth)
            putInt(HOUR, settings.hourOfInterest)
            putBoolean(L_ALLOW, settings.isLocationAllowed)
            putBoolean(L_USE, settings.useLocation)
        }.apply()
    }
}