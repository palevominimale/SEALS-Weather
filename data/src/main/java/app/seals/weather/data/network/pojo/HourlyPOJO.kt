package app.seals.weather.data.network.pojo

import com.google.gson.annotations.SerializedName

data class HourlyPOJO (
    @SerializedName("time"                 ) var time                : ArrayList<Long>   = arrayListOf(0),
    @SerializedName("temperature_2m"       ) var temperature2m       : ArrayList<Double> = arrayListOf(0.0),
    @SerializedName("relativehumidity_2m"  ) var relativehumidity2m  : ArrayList<Int>    = arrayListOf(0),
    @SerializedName("apparent_temperature" ) var apparentTemperature : ArrayList<Double> = arrayListOf(0.0),
    @SerializedName("weathercode"          ) var weathercode         : ArrayList<Int>    = arrayListOf(0),
    @SerializedName("surface_pressure"     ) var surfacePressure     : ArrayList<Double> = arrayListOf(0.0),
    @SerializedName("windspeed_10m"        ) var windspeed10m        : ArrayList<Double> = arrayListOf(0.0),
    @SerializedName("winddirection_10m"    ) var winddirection10m    : ArrayList<Double>    = arrayListOf(0.0)
)