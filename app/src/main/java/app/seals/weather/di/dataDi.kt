package app.seals.weather.di

import app.seals.weather.data.repos.ForecastRepository
import app.seals.weather.data.repos.SettingsRepository
import app.seals.weather.data.room.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataDi = module {
    single <ForecastRepositoryDAO>{
        ForecastRepository(androidContext())
    }
    single <SettingsRepositoryInterface> {
        SettingsRepository(androidContext())
    }
    single {
        SettingsRepository(androidContext())
    }
}