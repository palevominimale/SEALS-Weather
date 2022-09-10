package app.seals.weather.data.models

class SettingsDataModel (
    val latitude: Float = 0.0F,
    val longitude: Float = 0.0F,
    val forecastStep: Int = 1,
    val forecastStepWidget: Int = 2,
    val forecastDepth: Int = 7,
    val hourOfInterest: Int = 15,
    val isLocationAllowed: Boolean = true,
    val useLocation: Boolean = true,
    val firstStart: Boolean = false
)