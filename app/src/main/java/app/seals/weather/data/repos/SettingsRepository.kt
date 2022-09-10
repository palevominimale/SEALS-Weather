package app.seals.weather.data.repos

import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import app.seals.weather.data.models.SettingsDataModel
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface

private const val PREFS = "preferences"
private const val LAT = "lat"
private const val LON = "lon"
private const val F_STEP = "forecastStep"
private const val F_STEP_W = "forecastStepWidget"
private const val F_DEPTH = "forecastDepth"
private const val HOUR = "hourOfInterest"
private const val L_ALLOW = "isLocationAllowed"
private const val L_USE = "useLocation"
private const val FIRST_START = "firstStart"

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
            useLocation = prefs.getBoolean(L_USE, false),
            firstStart = prefs.getBoolean(FIRST_START, false)
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
            putBoolean(FIRST_START, settings.firstStart)
        }.apply()
    }

    override fun getLocation(): Location {
        return Location(LocationManager.GPS_PROVIDER).apply {
            this.latitude = prefs.getFloat(LAT, 0.0F).toDouble()
            this.longitude = prefs.getFloat(LON, 0.0F).toDouble()
        }
    }

    override fun setLocation(location: Location) {
        prefs.edit().apply {
            Log.e("LOC_PR", "${location.latitude} ${location.longitude}")
            putFloat(LAT, location.latitude.toFloat())
            putFloat(LON, location.longitude.toFloat())
        }.apply()
    }

    override fun getLocationUsageStatus() : Boolean {
        return prefs.getBoolean(L_USE, false)
    }

    override fun setLocationAllowed() {
        prefs.edit().putBoolean(L_USE, true).apply()
    }

    override fun setLocationDisallowed() {
        prefs.edit().putBoolean(L_USE, false).apply()
    }

    override fun getForecastDepth(): Int {
        return prefs.getInt(F_DEPTH, 7)
    }

    override fun getFirstStart(): Boolean {
        return prefs.getBoolean(FIRST_START, true)
    }
}