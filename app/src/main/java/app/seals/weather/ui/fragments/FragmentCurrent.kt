package app.seals.weather.ui.fragments

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.seals.weather.R
import app.seals.weather.data.models.ForecastItemDataModel
import app.seals.weather.ui.vm.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.Instant
import java.time.ZoneId

class FragmentCurrent : Fragment() {

    private val vm: SharedViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_current, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.forecastCurrentLive.observe(viewLifecycleOwner) {
            load(it ?: ForecastItemDataModel())
        }
    }

    override fun onResume() {
        load(vm.forecastCurrentLive.value ?: ForecastItemDataModel())
        super.onResume()
    }

    @SuppressLint("SetTextI18n")
    private fun load(data: ForecastItemDataModel) {
        val act = requireActivity()
        val currentTemp = act.findViewById<TextView>(R.id.currentTemp)
        val currentTempMax = act.findViewById<TextView>(R.id.currentTempMax)
        val currentTempMin = act.findViewById<TextView>(R.id.currentTempMin)
        val currentHumidity = act.findViewById<TextView>(R.id.currentHumidity)
        val currentPressure = act.findViewById<TextView>(R.id.currentPressure)
        val currentSunset = act.findViewById<TextView>(R.id.currentSunset)
        val currentSunrise = act.findViewById<TextView>(R.id.currentSunrise)
        val currentWeatherType = act.findViewById<TextView>(R.id.currentWeatherType)
        val currentWeatherIcon = act.findViewById<ImageView>(R.id.currentWeatherIcon)
        val currentWindSpd = act.findViewById<ImageView>(R.id.currentWindSpd)
        val currentWindDir = act.findViewById<ImageView>(R.id.currentWindDir)
        val currentCity = act.findViewById<TextView>(R.id.currentCity)

        (data).run {
            var city = "D"

            try {
                city = Geocoder(context).getFromLocation(
                    vm.location.latitude,
                    vm.location.longitude,
                    1
                )[0].locality ?: "null"
            } catch (e: Exception) {
                Log.e("EXC", e.toString())
            }

            val sunset = Instant.ofEpochSecond(sunset ?: 0L).atZone(ZoneId.systemDefault())
            val sunrise = Instant.ofEpochSecond(sunrise ?: 0L).atZone(ZoneId.systemDefault())
            currentCity.text = city
            currentTemp.text = "$temp°C"
            currentTempMax.text = "$tempMax°C"
            currentTempMin.text = "$tempMin°C"
            currentHumidity.text = "RH: $humidity%"
            currentPressure.text = "$pressure hPa"
            currentSunrise.text = sunrise.toString().subSequence(11, 16)
            currentSunset.text = sunset.toString().subSequence(11, 16)
            currentWeatherType.text = weatherType
            currentWeatherIcon.setImageResource(weatherIcon ?: R.drawable.wi_meteor)
            currentWindSpd.setImageResource(windSpd ?: R.drawable.wi_wind_beaufort_0)
            currentWindDir.setImageResource((R.drawable.ic_wi_wind_deg))
            currentWindDir.rotation = windDir?.toFloat() ?: 0.0F
        }
    }
}
