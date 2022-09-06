package app.seals.weather.di

import app.seals.weather.widget.WidgetRefresh
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val widgetDi = module {
    factory {
        WidgetRefresh(context = androidContext())
    }
}