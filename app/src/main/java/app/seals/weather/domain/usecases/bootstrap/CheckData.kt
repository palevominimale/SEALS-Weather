package app.seals.weather.domain.usecases.bootstrap

import app.seals.weather.data.models.SettingsDataModel
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.forecast.RefreshForecast

class CheckData(
    private val forecastRepository: ForecastRepositoryDAO,
    private val retrofitNetworkRefresh: RefreshForecast,
    private val settingsRepository: SettingsRepositoryInterface
) {
    fun execute() {
        if(settingsRepository.getFirstStart()) {
            settingsRepository.set(SettingsDataModel())
        }
        if(forecastRepository.getById(0) == null) {
            suspend {
                retrofitNetworkRefresh.execute()
            }
        }
    }
}