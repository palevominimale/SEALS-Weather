package app.seals.weather.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.seals.weather.app.location.GetLocation
import app.seals.weather.data.models.ForecastItemDomainModel
import app.seals.weather.data.room.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.NetworkApiInterface
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.forecast.LoadDailyForecastUseCase
import app.seals.weather.domain.usecases.forecast.LoadHourlyForecastUseCase
import app.seals.weather.widget.WidgetRefresh
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
import java.time.LocalDateTime

class SharedViewModel  (
    private val forecastRepository: ForecastRepositoryDAO,
    private val settingsRepository: SettingsRepositoryInterface,
    private val getLocation: GetLocation,
    private val network: NetworkApiInterface,
    private val widgetRefresh: WidgetRefresh
) : ViewModel() {

    private var forecastHourly = mutableListOf<ForecastItemDomainModel>()
    private var forecastDaily = mutableListOf<ForecastItemDomainModel>()
    private var forecastCurrent = ForecastItemDomainModel()
    private var isRefreshing = false
    var location = settingsRepository.getLocation()

    val forecastHourlyLive by lazy { MutableLiveData(forecastHourly) }
    val forecastDailyLive by lazy { MutableLiveData(forecastDaily) }
    val forecastCurrentLive by lazy { MutableLiveData(forecastCurrent) }
    val isRefreshingLive by lazy { MutableLiveData(isRefreshing) }

    private val loadDailyForecastUseCase : LoadDailyForecastUseCase
        by inject(LoadDailyForecastUseCase::class.java)
    private val loadHourlyForecastUseCase : LoadHourlyForecastUseCase
        by inject(LoadHourlyForecastUseCase::class.java)

    init {
        loadCurrent()
        loadHourly()
        loadDaily()
    }

    fun refresh() {
        if (!isRefreshing) {
            isRefreshing = true
            isRefreshingLive.postValue(isRefreshing)
            CoroutineScope(Dispatchers.IO).launch {
                getLocation.execute()
                network.execute()
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