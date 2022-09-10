package app.seals.weather.domain.interfaces

import android.location.Location
import app.seals.weather.data.models.SettingsDataModel


interface SettingsRepositoryInterface {
    fun get() : SettingsDataModel
    fun set(settings: SettingsDataModel)
    fun getLocation(): Location
    fun setLocation(location: Location)
    fun setLocationAllowed()
    fun setLocationDisallowed()
    fun getLocationUsageStatus() : Boolean
    fun getForecastDepth() : Int
    fun getFirstStart() : Boolean
}