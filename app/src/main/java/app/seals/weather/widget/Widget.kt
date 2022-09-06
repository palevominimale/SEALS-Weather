package app.seals.weather.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.widget.RemoteViews
import app.seals.weather.R
import app.seals.weather.data.models.ForecastItemDomainModel
import app.seals.weather.data.room.ForecastRepositoryDAO
import java.time.LocalDateTime
import org.koin.java.KoinJavaComponent.inject

class WeatherWidget : AppWidgetProvider() {

    private val forecastRepository: ForecastRepositoryDAO by inject(ForecastRepositoryDAO::class.java)

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
            Log.e("WDG", "Updated $appWidgetId")
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int ){
        val empty = ForecastItemDomainModel()
        val now = LocalDateTime.now().hour
        val views = RemoteViews(context.packageName, R.layout.weather_widget)
        val intent = Intent(context, WeatherWidget::class.java)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(context, WeatherWidget::class.java))
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        val pi = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_IMMUTABLE)
        views.setOnClickPendingIntent(R.id.tempWidget, pi)
        views.setOnClickPendingIntent(R.id.widgetRefresh, pi)

        (forecastRepository.getById(now.toLong()) ?: empty).run {
            val bmpOriginal: Bitmap = BitmapFactory.decodeResource(context.applicationContext.resources, R.drawable.winddir)
            val bmpResult = Bitmap.createBitmap(bmpOriginal.width, bmpOriginal.height, Bitmap.Config.ARGB_8888)
            val tempCanvas = Canvas(bmpResult)
            tempCanvas.rotate(windDir.toFloat(), bmpOriginal.width / 2.0F, bmpOriginal.height / 2.0F)
            tempCanvas.drawBitmap(bmpOriginal, 0F, 0F, null)
            views.setTextViewText(R.id.humidityWidget, "RH: ${(humidity ?: 0).toInt()}%")
            views.setTextViewText(R.id.pressureWidget, "$pressure hPa")
            views.setTextViewText(R.id.tempWidget, "${temp}°C")
            views.setTextViewText(R.id.widgetWeatherType, weatherType)
            views.setImageViewResource(R.id.widgetWeatherIcon, weatherIcon)
            views.setImageViewResource(R.id.widgetWindSpd, windSpd)
            views.setImageViewResource(R.id.widgetRefresh, R.drawable.wi_refresh)

            views.setImageViewBitmap(R.id.widgetWindDir, bmpResult)
        }
        (forecastRepository.getById(now+1L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon, weatherIcon)
        }
        (forecastRepository.getById(now+2L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp1, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon1, weatherIcon)
        }
        (forecastRepository.getById(now+3L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp2, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon2, weatherIcon)
        }
        (forecastRepository.getById(now+4L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp3, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon3, weatherIcon)
        }
        (forecastRepository.getById(now+5L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp4, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon4, weatherIcon)
        }
        (forecastRepository.getById(now+6L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp5, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon5, weatherIcon)
        }
        (forecastRepository.getById(now+7L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp6, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon6, weatherIcon)
        }
        (forecastRepository.getById(now+8L) ?: empty).run {
            views.setTextViewText(R.id.wlTemp7, "${temp}°C")
            views.setImageViewResource(R.id.wlIcon7, weatherIcon)
        }
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

