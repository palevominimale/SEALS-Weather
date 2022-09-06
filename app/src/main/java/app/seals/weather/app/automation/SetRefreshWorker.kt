package app.seals.weather.app.automation

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class SetRefreshWorker(private val context: Context) {

    fun execute() {
        WorkManager.getInstance(context).cancelAllWork()
        val workRequest = PeriodicWorkRequest
            .Builder(RefreshData::class.java, 15, TimeUnit.MINUTES)
            .setInitialDelay(2, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

}