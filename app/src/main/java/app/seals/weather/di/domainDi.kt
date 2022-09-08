package app.seals.weather.di

import app.seals.weather.domain.usecases.forecast.LoadDailyForecast
import app.seals.weather.domain.usecases.forecast.LoadHourlyForecast
import app.seals.weather.domain.usecases.bootstrap.CheckData

import org.koin.dsl.module

val domainDi = module {

    factory {
        CheckData(
            forecastRepository = get(),
            retrofitNetworkRefresh = get()
        )
    }

    factory {
        LoadDailyForecast(
            forecastRepository = get(),
            settingsRepository = get()
        )
    }

    factory {
        LoadHourlyForecast(
            forecastRepository = get(),
            settingsRepository = get()
        )
    }
}