package app.seals.weather.di

import app.seals.weather.domain.usecases.forecast.LoadDailyForecastUseCase
import app.seals.weather.domain.usecases.forecast.LoadHourlyForecastUseCase
import app.seals.weather.domain.usecases.settings.ChangeSettingsUseCase
import app.seals.weather.domain.usecases.settings.LoadSettingsUseCase
import app.seals.weather.app.bootstrap.CheckData

import org.koin.dsl.module

val domainDi = module {

    factory {
        CheckData(
            forecastRepository = get(),
            network = get()
        )
    }

    factory {
        LoadDailyForecastUseCase(
            forecastRepository = get(),
            settingsRepository = get()
        )
    }

    factory {
        LoadHourlyForecastUseCase(
            forecastRepository = get(),
            settingsRepository = get()
        )
    }

    factory {
        ChangeSettingsUseCase(

        )
    }

    factory {
        LoadSettingsUseCase(

        )
    }
}