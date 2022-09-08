package app.seals.weather.di

import app.seals.weather.domain.usecases.forecast.RefreshForecastUseCase
import app.seals.weather.network.RetrofitNetworkRefresh
import org.koin.dsl.module

val retrofitDi = module {
    single {
        RetrofitNetworkRefresh(settingsRepository = get())
    }
    single {
        RefreshForecastUseCase(
            retrofit = get(),
            settingsRepository = get(),
            forecastRepository = get()
        )
    }
}