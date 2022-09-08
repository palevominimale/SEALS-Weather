package app.seals.weather.domain.usecases.forecast

import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.data.models.ForecastItemDomainModel
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO

class LoadDailyForecast(
    private val forecastRepository: ForecastRepositoryDAO,
    private val settingsRepository: SettingsRepositoryInterface
    ) {

    fun execute() : Collection<ForecastItemDomainModel> {
        val forecastDepth = settingsRepository.get().forecastDepth
        val hourOfInterest = settingsRepository.get().hourOfInterest
        val range = (hourOfInterest..hourOfInterest+24*forecastDepth step 24).toList()
        return forecastRepository.getByIdRange(range) as Collection<ForecastItemDomainModel>
    }
}