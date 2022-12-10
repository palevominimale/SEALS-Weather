package app.seals.weather.data.network.interfaces

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Query

private const val REQUEST_COMPONENTS_DAILY = """temperature_2m_max,temperature_2m_min,sunrise,sunset,precipitation_hours"""
private const val REQUEST_COMPONENTS_HOURLY = """temperature_2m,relativehumidity_2m,dewpoint_2m,apparent_temperature,surface_pressure,precipitation,weathercode,cloudcover,shortwave_radiation,windspeed_10m,winddirection_10m,windgusts_10m"""
private const val WINDSPEED_UNITS = "ms"
private const val TIME_FORMAT = "unixtime"

interface RetrofitApiInterface {
    @GET("forecast?timeformat=$TIME_FORMAT&windspeed_unit=$WINDSPEED_UNITS&daily=$REQUEST_COMPONENTS_DAILY&hourly=$REQUEST_COMPONENTS_HOURLY")
    suspend fun getForecast(
        @Query ("latitude") lat: Double,
        @Query ("longitude") lon: Double,
        @Query ("timezone") timezone: String,
        @Query ("start_date") start_date: String,
        @Query ("end_date") end_date: String,
    ) : JsonObject

    companion object {
        const val BASE_URL = """https://api.open-meteo.com/v1/"""
    }

}
