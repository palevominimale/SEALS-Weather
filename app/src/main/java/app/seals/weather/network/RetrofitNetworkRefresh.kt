package app.seals.weather.network

import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.network.pojo.BasePOJO
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class RetrofitNetworkRefresh(
    private val settingsRepository: SettingsRepositoryInterface
) {
    suspend fun execute() : BasePOJO {

        val location = settingsRepository.getLocation()
        val depth = settingsRepository.getForecastDepth() * 24L
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateStart = LocalDateTime.now()
        val dateEnd: LocalDateTime = LocalDateTime.now().plusHours(depth)
        val dateStartFormatted = dateStart.format(format).toString()
        val dateEndFormatted = dateEnd.format(format).toString()
        val timeZone = ZoneId.systemDefault().id
        return Gson().fromJson(RetrofitNetworkRequest.retrofit.getForecast(
            lat = location.latitude,
            lon = location.longitude,
            timezone = timeZone,
            start_date = dateStartFormatted,
            end_date = dateEndFormatted
        ), BasePOJO::class.java)
    }
}