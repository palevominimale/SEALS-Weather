package app.seals.weather.app

import android.app.Application
import app.seals.weather.app.automation.SetRefreshWorker
import app.seals.weather.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        val setRefreshWorker = SetRefreshWorker(applicationContext)
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(
                uiDi,
                domainDi,
                dataDi,
                locationDi,
                widgetDi,
                retrofitDi))
        }
        setRefreshWorker.execute()
    }
}