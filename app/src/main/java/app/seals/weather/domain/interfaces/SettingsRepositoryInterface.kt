package app.seals.weather.domain.interfaces

import app.seals.weather.data.models.SettingsDataModel


interface SettingsRepositoryInterface {
    fun get() : SettingsDataModel
    fun set(settings: SettingsDataModel)
}