package app.seals.weather.domain.usecases.forecast

import app.seals.weather.R
import app.seals.weather.data.models.ForecastItemDataModel
import app.seals.weather.domain.interfaces.ForecastRepositoryDAO
import app.seals.weather.domain.interfaces.SettingsRepositoryInterface
import app.seals.weather.domain.usecases.network.RetrofitNetworkRefresh
import java.time.LocalDateTime
import java.time.ZoneOffset

class RefreshForecast (
    private val retrofit: RetrofitNetworkRefresh,
    private val settingsRepository: SettingsRepositoryInterface,
    private val forecastRepository: ForecastRepositoryDAO
        ) {

    suspend fun execute() {
        val data = retrofit.execute()
        forecastRepository.clear()
        val depth = settingsRepository.getForecastDepth()*24
        for (i in 0..depth) {
            forecastRepository.put(ForecastItemDataModel(
                id = i.toLong(),
                time = data.hourly.time[i],
                temp = data.hourly.temperature2m[i].toInt(),
                tempMin = data.daily.temperature2mMin[i/24].toInt(),
                tempMax = data.daily.temperature2mMax[i/24].toInt(),
                humidity = data.hourly.relativehumidity2m[i],
                pressure = data.hourly.surfacePressure[i].toInt(),
                sunset = data.daily.sunset[i/24],
                sunrise = data.daily.sunrise[i/24],
                weatherType = selectWeatherType(data.hourly.weathercode[i]),
                windSpd = selectWindIcon(data.hourly.windspeed10m[i]),
                windDir = data.hourly.winddirection10m[i],
                weatherIcon = selectWeatherIcon(data.hourly.weathercode[i], data.hourly.time[i])
            ))
        }
    }

    private fun selectWeatherIcon(weatherCode: Int, date: Long) : Int {

        val daytime = if(LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC).hour in 8..19) "d" else "n"
        if(daytime == "n") {
            return when (weatherCode) {
                0 -> R.drawable.wi_stars
                1 -> R.drawable.wi_night_alt_cloudy_high
                2 -> R.drawable.wi_night_alt_partly_cloudy
                3 -> R.drawable.wi_night_alt_cloudy
                45, 48	-> R.drawable.wi_night_fog
                51 -> R.drawable.wi_night_alt_rain_mix
                53 -> R.drawable.wi_night_alt_rain_mix
                55 -> R.drawable.wi_night_alt_rain_mix
                56 -> R.drawable.wi_night_alt_rain_mix
                57 -> R.drawable.wi_night_alt_rain_mix
                61 -> R.drawable.wi_night_alt_rain
                63 -> R.drawable.wi_night_alt_rain
                65 -> R.drawable.wi_night_alt_rain
                66 -> R.drawable.wi_night_alt_rain_mix
                67 -> R.drawable.wi_night_alt_rain_mix
                71 -> R.drawable.wi_night_alt_snow
                73 -> R.drawable.wi_night_alt_snow
                75 -> R.drawable.wi_night_alt_snow
                77 -> R.drawable.wi_night_alt_hail
                80 -> R.drawable.wi_night_alt_showers
                81 -> R.drawable.wi_night_alt_showers
                82 -> R.drawable.wi_night_alt_showers
                85 -> R.drawable.wi_night_alt_snow
                86 -> R.drawable.wi_night_alt_snow
                95 -> R.drawable.wi_night_alt_lightning
                96 -> R.drawable.wi_night_alt_thunderstorm
                99 -> R.drawable.wi_night_alt_thunderstorm
                else -> R.drawable.wi_meteor
            }
        }else {
            return when(weatherCode) {
                0 -> R.drawable.wi_day_sunny
                1 -> R.drawable.wi_day_cloudy_high
                2 -> R.drawable.wi_day_sunny_overcast
                3 -> R.drawable.wi_day_cloudy
                45, 48	-> R.drawable.wi_night_fog
                51 -> R.drawable.wi_day_rain_mix
                53 -> R.drawable.wi_day_rain_mix
                55 -> R.drawable.wi_day_rain_mix
                56 -> R.drawable.wi_day_rain_mix
                57 -> R.drawable.wi_day_rain_mix
                61 -> R.drawable.wi_day_rain
                63 -> R.drawable.wi_day_rain
                65 -> R.drawable.wi_day_rain
                66 -> R.drawable.wi_day_rain_mix
                67 -> R.drawable.wi_day_rain_mix
                71 -> R.drawable.wi_day_snow
                73 -> R.drawable.wi_day_snow
                75 -> R.drawable.wi_day_snow
                77 -> R.drawable.wi_day_hail
                80 -> R.drawable.wi_day_showers
                81 -> R.drawable.wi_day_showers
                82 -> R.drawable.wi_day_showers
                85 -> R.drawable.wi_day_snow
                86 -> R.drawable.wi_day_snow
                95 -> R.drawable.wi_day_lightning
                96 -> R.drawable.wi_day_thunderstorm
                99 -> R.drawable.wi_day_thunderstorm
                else -> R.drawable.wi_meteor
            }
        }
    }

    private fun selectWindIcon(windIntensity: Double): Int {
        return when(windIntensity) {
            in 0.0..0.99 -> R.drawable.wi_wind_beaufort_0
            in 1.0..1.99 -> R.drawable.wi_wind_beaufort_1
            in 2.0..2.99 -> R.drawable.wi_wind_beaufort_2
            in 3.0..3.99 -> R.drawable.wi_wind_beaufort_3
            in 4.0..4.99 -> R.drawable.wi_wind_beaufort_4
            in 5.0..5.99 -> R.drawable.wi_wind_beaufort_5
            in 6.0..6.99 -> R.drawable.wi_wind_beaufort_6
            in 7.0..7.99 -> R.drawable.wi_wind_beaufort_7
            in 8.0..8.99 -> R.drawable.wi_wind_beaufort_8
            in 9.0..9.99 -> R.drawable.wi_wind_beaufort_9
            in 10.0..10.99 -> R.drawable.wi_wind_beaufort_10
            in 11.0..11.99 -> R.drawable.wi_wind_beaufort_11
            in 12.0..12.99 -> R.drawable.wi_wind_beaufort_12
            else -> R.drawable.wi_strong_wind
        }
    }

    private fun selectWeatherType(weatherCode: Int) : String {
        return when(weatherCode) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45, 48	-> "Fog"
            51 -> "Light drizzle"
            53 -> "Moderate drizzle"
            55 -> "Dense drizzle"
            56 -> "Freezing drizzle"
            57 -> "Freezing drizzle"
            61 -> "Slight rain"
            63 -> "Moderate rain"
            65 -> "Heavy rain"
            66 -> "Freeing rain"
            67 -> "Freezing rain"
            71 -> "Slight snowfall"
            73 -> "Moderate snowfall"
            75 -> "Heavy snowfall"
            77 -> "Snow grains"
            80 -> "Slight rain shower"
            81 -> "Moderate rain shower"
            82 -> "Violent rain shower"
            85 -> "Slight snow shower"
            86 -> "Heavy snow shower"
            95 -> "Thunderstorm"
            96 -> "Thunderstorm, hail"
            99 -> "Thunderstorm, hail"
            else -> "Aliens invasion!"
        }
    }

}