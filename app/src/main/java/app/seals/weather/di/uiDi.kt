package app.seals.weather.di

import app.seals.weather.domain.usecases.forecast.LoadDailyForecast
import app.seals.weather.domain.usecases.forecast.LoadHourlyForecast
import app.seals.weather.domain.usecases.forecast.RefreshForecast
import app.seals.weather.ui.vm.SharedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent

val uiDi = module {
    viewModel {
        SharedViewModel(
            forecastRepository = get(),
            settingsRepository = get(),
            updateLocation = get(),
            widgetRefresh = get(),
            context = androidContext(),
            loadDailyForecast = get(),
            loadHourlyForecast = get(),
            retrofit = get()
        )
    }
}

//private val loadDailyForecast : LoadDailyForecast
//        by KoinJavaComponent.inject(LoadDailyForecast::class.java)
//private val loadHourlyForecast : LoadHourlyForecast
//        by KoinJavaComponent.inject(LoadHourlyForecast::class.java)
//private val retrofit : RefreshForecast
//        by KoinJavaComponent.inject(RefreshForecast::class.java)