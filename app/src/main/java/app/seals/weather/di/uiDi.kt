package app.seals.weather.di

import app.seals.weather.ui.vm.SharedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {
    viewModel {
        SharedViewModel(
            forecastRepository = get(),
            settingsRepository = get(),
            updateLocation = get(),
            widgetRefresh = get(),
            context = androidContext()
        )
    }
}