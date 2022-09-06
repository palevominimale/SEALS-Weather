package app.seals.weather.domain.usecases.forecast

import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.data.models.ForecastItemDomainModel
import app.seals.weather.data.room.ForecastRepositoryDAO
import java.time.LocalDateTime

class LoadHourlyForecastUseCase(
    private val forecastRepository: ForecastRepositoryDAO,
    private val settingsRepository: SettingsRepositoryInterface
    ) {

    fun execute() : Collection<ForecastItemDomainModel> {
        val now = LocalDateTime.now().hour
        val forecastStep = settingsRepository.get().forecastStep
        val range = (now..now+48 step forecastStep).toList()
        return forecastRepository.getByIdRange(range) as Collection<ForecastItemDomainModel>
    }
}