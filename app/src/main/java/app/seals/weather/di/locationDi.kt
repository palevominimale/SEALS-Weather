package app.seals.weather.di

import app.seals.weather.app.location.GetLocation
import com.google.android.gms.location.FusedLocationProviderClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationDi = module {
    single {
        FusedLocationProviderClient(androidContext())
    }

    factory {
        GetLocation(
            fusedLocationProviderClient = get(),
            settingsRepository = get()
        )
    }
}