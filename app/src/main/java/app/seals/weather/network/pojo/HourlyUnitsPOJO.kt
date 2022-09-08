package app.seals.weather.network.pojo

import com.google.gson.annotations.SerializedName

data class HourlyUnitsPOJO (
    @SerializedName("time"                 ) var time                : String? = null,
    @SerializedName("temperature_2m"       ) var temperature2m       : String? = null,
    @SerializedName("relativehumidity_2m"  ) var relativehumidity2m  : String? = null,
    @SerializedName("apparent_temperature" ) var apparentTemperature : String? = null,
    @SerializedName("weathercode"          ) var weathercode         : String? = null,
    @SerializedName("surface_pressure"     ) var surfacePressure     : String? = null,
    @SerializedName("windspeed_10m"        ) var windspeed10m        : String? = null,
    @SerializedName("winddirection_10m"    ) var winddirection10m    : String? = null
)