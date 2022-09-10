package app.seals.weather.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.seals.weather.R

@Entity(tableName = "Forecast")
class ForecastItemDataModel (
    @PrimaryKey val id: Long = 0L,
    @ColumnInfo(name = "time") val time: Long? = 0L,
    @ColumnInfo(name = "temp") val temp: Int? = 0,
    @ColumnInfo(name = "tempMin") val tempMin: Int? = 0,
    @ColumnInfo(name = "tempMax") val tempMax: Int? = 0,
    @ColumnInfo(name = "humidity") val humidity: Int? = 0,
    @ColumnInfo(name = "pressure") val pressure: Int? = 0,
    @ColumnInfo(name = "weatherIcon") val weatherIcon: Int? = R.drawable.wi_meteor,
    @ColumnInfo(name = "windSpd") val windSpd: Int? = R.drawable.wi_wind_beaufort_1,
    @ColumnInfo(name = "windDir") val windDir: Double? = 0.0,
    @ColumnInfo(name = "sunrise") val sunrise: Long? = 0L,
    @ColumnInfo(name = "sunset") val sunset: Long? = 43200L,
    @ColumnInfo(name = "weatherType") val weatherType: String? = "Aliens Invasion!"
)