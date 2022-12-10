package app.seals.weather.data.network.pojo

import com.google.gson.annotations.SerializedName

data class DailyPOJO(
    @SerializedName("time"               ) var time             : ArrayList<String> = arrayListOf(),
    @SerializedName("weathercode"        ) var weathercode      : ArrayList<Int>    = arrayListOf(),
    @SerializedName("temperature_2m_max" ) var temperature2mMax : ArrayList<Double> = arrayListOf(),
    @SerializedName("temperature_2m_min" ) var temperature2mMin : ArrayList<Double> = arrayListOf(),
    @SerializedName("sunrise"            ) var sunrise          : ArrayList<Long>   = arrayListOf(),
    @SerializedName("sunset"             ) var sunset           : ArrayList<Long>   = arrayListOf()
)