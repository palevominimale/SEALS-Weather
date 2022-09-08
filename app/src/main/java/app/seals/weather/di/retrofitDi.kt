package app.seals.weather.di

import app.seals.weather.domain.usecases.forecast.RefreshForecast
import app.seals.weather.domain.usecases.network.RetrofitNetworkRefresh
import org.koin.dsl.module

val retrofitDi = module {
    single {
        RetrofitNetworkRefresh(settingsRepository = get())
    }
    single {
        RefreshForecast(
            retrofit = get(),
            settingsRepository = get(),
            forecastRepository = get()
        )
    }
}