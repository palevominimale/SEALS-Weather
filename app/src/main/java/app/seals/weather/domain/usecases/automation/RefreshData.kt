package app.seals.weather.domain.usecases.automation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.work.Worker
import androidx.work.WorkerParameters
import app.seals.weather.R
import app.seals.weather.domain.usecases.forecast.RefreshForecast
import app.seals.weather.domain.usecases.location.UpdateLocation
import app.seals.weather.widget.WidgetRefresh
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class RefreshData(
    context: Context,
    parameters: WorkerParameters
) : Worker(context, parameters) {

    private val retrofitNetworkRefresh: RefreshForecast by inject(RefreshForecast::class.java)
    private val widgetRefresh : WidgetRefresh by inject(WidgetRefresh::class.java)
    private val locationUpdate : UpdateLocation by inject(UpdateLocation::class.java)

    override fun doWork(): Result {
            sendIntent(true)
            locationUpdate.setOnSuccessListener {
                CoroutineScope(Dispatchers.IO).launch {
                retrofitNetworkRefresh.setOnSuccessListener {
                    sendIntent(false)
                    widgetRefresh.execute()
                }
            }
        }
        return Result.success()
    }

    private fun sendIntent(state: Boolean) {
        val intent = Intent(applicationContext.getString(R.string.intent_refreshing))
            .putExtra("isRefreshing", state)
        PendingIntent.getBroadcast(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
            .send()
    }
}