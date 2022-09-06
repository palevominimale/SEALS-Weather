package app.seals.weather.di

import app.seals.weather.domain.interfaces.NetworkApiInterface
import app.seals.weather.network.NetworkRefresh
import org.koin.dsl.module

val networkDi = module {
    single <NetworkApiInterface> {
        NetworkRefresh (
            forecastRepository = get(),
            settingsRepository = get()
        )
    }
}