package app.seals.weather.domain.usecases.bootstrap

import app.seals.weather.domain.interfaces.ForecastRepositoryDAO
import app.seals.weather.domain.usecases.forecast.RefreshForecast

class CheckData(
    private val forecastRepository: ForecastRepositoryDAO,
    private val retrofitNetworkRefresh: RefreshForecast
) {
    fun execute() {
        if(forecastRepository.getById(0) == null) {
            suspend {
                retrofitNetworkRefresh.execute()
            }
        }
    }
}