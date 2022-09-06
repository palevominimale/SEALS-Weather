package app.seals.weather.app.automation

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import app.seals.weather.domain.interfaces.NetworkApiInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class RefreshData(
    context: Context,
    parameters: WorkerParameters
) : Worker(context, parameters) {

    private val network: NetworkApiInterface by inject(NetworkApiInterface::class.java)

    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            network.execute()
            Log.e("WRK", "executed")
        }
        return Result.success()
    }
}