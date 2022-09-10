package app.seals.weather.ui.vm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.seals.weather.R
import app.seals.weather.domain.usecases.location.UpdateLocation
import app.seals.weather.data.models.ForecastItemDataModel
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.forecast.LoadDailyForecast
import app.seals.weather.domain.usecases.forecast.LoadHourlyForecast
import app.seals.weather.domain.usecases.forecast.RefreshForecast
import app.seals.weather.widget.WidgetRefresh
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime

class SharedViewModel(
    private val forecastRepository: ForecastRepositoryDAO,
    private val settingsRepository: SettingsRepositoryInterface,
    private val updateLocation: UpdateLocation,
    private val widgetRefresh: WidgetRefresh,
    context: Context
) : ViewModel() {

    private var forecastHourly = mutableListOf<ForecastItemDataModel>()
    private var forecastDaily = mutableListOf<ForecastItemDataModel>()
    private var forecastCurrent = ForecastItemDataModel()
    private val filter = IntentFilter(context.getString(R.string.intent_refreshing))
    var location = settingsRepository.getLocation()

    val forecastHourlyLive by lazy { MutableLiveData(forecastHourly) }
    val forecastDailyLive by lazy { MutableLiveData(forecastDaily) }
    val forecastCurrentLive by lazy { MutableLiveData(forecastCurrent) }
    val isRefreshingLive by lazy { MutableLiveData(false) }

    private val loadDailyForecast : LoadDailyForecast
        by inject(LoadDailyForecast::class.java)
    private val loadHourlyForecast : LoadHourlyForecast
        by inject(LoadHourlyForecast::class.java)
    private val retrofit : RefreshForecast
        by inject(RefreshForecast::class.java)

    init {
        loadCurrent()
        loadHourly()
        loadDaily()
        setIntentListener(context)
    }

    private fun setIntentListener(context: Context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                isRefreshingLive.postValue(
                    intent.getBooleanExtra("isRefreshing", false)
                )
            }
        }
        context.registerReceiver(receiver, filter)
    }

    fun refresh() {
        isRefreshingLive.postValue(true)

        CoroutineScope(Dispatchers.IO).launch {
            updateLocation.execute()
            retrofit.execute()
        }.invokeOnCompletion {
            widgetRefresh.execute()
            location = settingsRepository.getLocation()
            MainScope().launch {
                loadHourly()
                loadDaily()
                loadCurrent()
            }

            isRefreshingLive.postValue(false)
        }
    }

    private fun loadCurrent() {
        CoroutineScope(Dispatchers.IO).launch {
            val now = LocalDateTime.now().hour.toLong()
            forecastCurrent = forecastRepository.getById(now) ?: ForecastItemDataModel()
        }.invokeOnCompletion {
            MainScope().launch {
                forecastCurrentLive.postValue(forecastCurrent)
            }
        }
    }

    private fun loadHourly() {
        CoroutineScope(Dispatchers.IO).launch {
            forecastHourly.clear()
            forecastHourly.addAll(loadHourlyForecast.execute())
        }.invokeOnCompletion {
            MainScope().launch {
                forecastHourlyLive.postValue(forecastHourly)
            }
        }
    }

    private fun loadDaily() {
        CoroutineScope(Dispatchers.IO).launch {
            forecastDaily.clear()
            forecastDaily.addAll(loadDailyForecast.execute())
        }.invokeOnCompletion {
            MainScope().launch {
                forecastDailyLive.postValue(forecastDaily)
            }
        }
    }
}