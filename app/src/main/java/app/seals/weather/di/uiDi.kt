package app.seals.weather.di

import app.seals.weather.ui.vm.SharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {
    viewModel {
        SharedViewModel(
            forecastRepository = get(),
            settingsRepository = get(),
            network = get(),
            getLocation = get(),
            widgetRefresh = get()
        )
    }
}