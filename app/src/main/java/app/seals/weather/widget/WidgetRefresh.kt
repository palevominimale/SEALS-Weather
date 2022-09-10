package app.seals.weather.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class WidgetRefresh(private val context: Context) {

    fun execute() {
    val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
    val ids = AppWidgetManager
        .getInstance(context)
        .getAppWidgetIds(ComponentName(context, WeatherWidget::class.java))
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
    PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE).send()
    }
}