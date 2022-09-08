package app.seals.weather.app.automation

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import app.seals.weather.R
import app.seals.weather.domain.usecases.forecast.RefreshForecastUseCase
import app.seals.weather.widget.WidgetRefresh
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class RefreshData(
    context: Context,
    parameters: WorkerParameters
) : Worker(context, parameters) {

    private val retrofitNetworkRefresh: RefreshForecastUseCase by inject(RefreshForecastUseCase::class.java)
    private val widgetRefresh : WidgetRefresh by inject(WidgetRefresh::class.java)

    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            sendIntent(true)
            retrofitNetworkRefresh.execute()
        }.invokeOnCompletion {
            sendIntent(false)
            widgetRefresh.execute()
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
        Log.e("INTENT", "$intent ${intent.extras}")
    }
}