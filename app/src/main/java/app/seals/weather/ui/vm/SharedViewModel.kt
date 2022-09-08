package app.seals.weather.ui.vm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.seals.weather.R
import app.seals.weather.app.location.UpdateLocation
import app.seals.weather.data.models.ForecastItemDomainModel
import app.seals.weather.data.room.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.forecast.LoadDailyForecastUseCase
import app.seals.weather.domain.usecases.forecast.LoadHourlyForecastUseCase
import app.seals.weather.domain.usecases.forecast.RefreshForecastUseCase
import app.seals.weather.widget.WidgetRefresh
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime

class SharedViewModel(
    private val forecastRepository: ForecastRepositoryDAO,
    private val settingsRepository: SettingsRepositoryInterface,
    private val updateLocation: UpdateLocation,
    private val widgetRefresh: WidgetRefresh,
    context: Context
) : ViewModel() {

    private var forecastHourly = mutableListOf<ForecastItemDomainModel>()
    private var forecastDaily = mutableListOf<ForecastItemDomainModel>()
    private var forecastCurrent = ForecastItemDomainModel()
    private var isRefreshing = false
    private val filter = IntentFilter(context.getString(R.string.intent_refreshing))
    var location = settingsRepository.getLocation()

    val forecastHourlyLive by lazy { MutableLiveData(forecastHourly) }
    val forecastDailyLive by lazy { MutableLiveData(forecastDaily) }
    val forecastCurrentLive by lazy { MutableLiveData(forecastCurrent) }
    val isRefreshingLive by lazy { MutableLiveData(isRefreshing) }

    private val loadDailyForecastUseCase : LoadDailyForecastUseCase
        by inject(LoadDailyForecastUseCase::class.java)
    private val loadHourlyForecastUseCase : LoadHourlyForecastUseCase
        by inject(LoadHourlyForecastUseCase::class.java)
    private val retrofit : RefreshForecastUseCase
        by inject(RefreshForecastUseCase::class.java)

    init {
        loadCurrent()
        loadHourly()
        loadDaily()
        setIntentListener(context)
    }

    private fun setIntentListener(context: Context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                isRefreshing = intent.getBooleanExtra("isRefreshing", false)
                isRefreshingLive.postValue(isRefreshing)
                Log.e("INTENT", "$intent ${intent.extras} ${intent.hashCode()}")
            }
        }
        context.registerReceiver(receiver, filter)
    }

    fun refresh() {
        if (!isRefreshing) {
            isRefreshing = true
            isRefreshingLive.postValue(isRefreshing)
            CoroutineScope(Dispatchers.IO).launch {
                updateLocation.execute()
//                network.execute()
                retrofit.execute()
            }.invokeOnCompletion {
                widgetRefresh.execute()
                MainScope().launch {
                    location = settingsRepository.getLocation()
                    loadHourly()
                    loadDaily()
                    loadCurrent()
                }
                isRefreshing = false
                isRefreshingLive.postValue(isRefreshing)
            }
        }
    }

    private fun loadCurrent() {
        CoroutineScope(Dispatchers.IO).launch {
            val now = LocalDateTime.now().hour.toLong()
            forecastCurrent = forecastRepository.getById(now) ?: ForecastItemDomainModel()
        }.invokeOnCompletion {
            MainScope().launch {
                forecastCurrentLive.postValue(forecastCurrent)
            }
        }
    }

    private fun loadHourly() {
        CoroutineScope(Dispatchers.IO).launch {
            forecastHourly.clear()
            forecastHourly.addAll(loadHourlyForecastUseCase.execute())
        }.invokeOnCompletion {
            MainScope().launch {
                forecastHourlyLive.postValue(forecastHourly)
            }
        }
    }

    private fun loadDaily() {
        CoroutineScope(Dispatchers.IO).launch {
            forecastDaily.clear()
            forecastDaily.addAll(loadDailyForecastUseCase.execute())
        }.invokeOnCompletion {
            MainScope().launch {
                forecastDailyLive.postValue(forecastDaily)
            }
        }
    }
}