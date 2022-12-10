package app.seals.weather.data.network.pojo

import com.google.gson.annotations.SerializedName

data class BasePOJO (
    @SerializedName("latitude"              ) var latitude             : Double           = 0.0,
    @SerializedName("longitude"             ) var longitude            : Double           = 0.0,
    @SerializedName("generationtime_ms"     ) var generationtimeMs     : Double           = 0.0,
    @SerializedName("utc_offset_seconds"    ) var utcOffsetSeconds     : Int              = 0,
    @SerializedName("timezone"              ) var timezone             : String           = "null",
    @SerializedName("timezone_abbreviation" ) var timezoneAbbreviation : String           = "null",
    @SerializedName("elevation"             ) var elevation            : Int              = 0,
    @SerializedName("hourly_units"          ) var hourlyUnits          : HourlyUnitsPOJO  = HourlyUnitsPOJO(),
    @SerializedName("hourly"                ) var hourly               : HourlyPOJO       = HourlyPOJO(),
    @SerializedName("daily_units"           ) var dailyUnits           : DailyUnitsPOJO   = DailyUnitsPOJO(),
    @SerializedName("daily"                 ) var daily                : DailyPOJO        = DailyPOJO()
)