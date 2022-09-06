package app.seals.weather.app.bootstrap

import app.seals.weather.data.room.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.NetworkApiInterface

class CheckData(
    private val forecastRepository: ForecastRepositoryDAO,
    private val network: NetworkApiInterface
) {
    fun execute() {
        if(forecastRepository.getById(0) == null) {
            suspend {
                network.execute()
            }
        }
    }
}