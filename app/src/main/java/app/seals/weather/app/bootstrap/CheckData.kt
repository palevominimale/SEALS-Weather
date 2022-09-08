package app.seals.weather.app.bootstrap

import app.seals.weather.data.room.ForecastRepositoryDAO
import app.seals.weather.domain.usecases.forecast.RefreshForecastUseCase

class CheckData(
    private val forecastRepository: ForecastRepositoryDAO,
    private val retrofitNetworkRefresh: RefreshForecastUseCase
) {
    fun execute() {
        if(forecastRepository.getById(0) == null) {
            suspend {
                retrofitNetworkRefresh.execute()
            }
        }
    }
}