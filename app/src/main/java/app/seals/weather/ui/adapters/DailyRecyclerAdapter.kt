package app.seals.weather.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.seals.weather.R
import app.seals.weather.data.models.ForecastItemDomainModel
import java.time.Instant
import java.time.ZoneId

class DailyRecyclerAdapter(private val forecastDaily: MutableList<ForecastItemDomainModel>) :
    RecyclerView.Adapter<DailyRecyclerAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val rvTemp: TextView = item.findViewById(R.id.forecastItemTemp)
        val rvTempMax: TextView = item.findViewById(R.id.forecastItemTempMax)
        val rvTempMin: TextView = item.findViewById(R.id.forecastItemTempMin)
        val rvHumidity: TextView = item.findViewById(R.id.forecastItemHumidity)
        val rvPressure: TextView = item.findViewById(R.id.forecastItemPressure)
        val rvDayOfWeek: TextView = item.findViewById(R.id.forecastItemDayOfWeek)
        val rvDate: TextView = item.findViewById(R.id.forecastItemDate)
        val rvWeatherType: TextView = item.findViewById(R.id.forecastItemWeatherType)
        val rvWeatherIcon: ImageView = item.findViewById(R.id.forecastItemWeatherIcon)
        val rvWindSpd: ImageView = item.findViewById(R.id.forecastItemWindSpd)
        val rvWindDir: ImageView = item.findViewById(R.id.forecastItemWindDir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_daily, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        forecastDaily[position].run {
            val date = Instant.ofEpochSecond(time ?: 0L).atZone(ZoneId.systemDefault())
            holder.rvTemp.text = "$temp°C"
            holder.rvTempMax.text = "$tempMax°C"
            holder.rvTempMin.text = "$tempMin°C"
            holder.rvHumidity.text = "RH $humidity%"
            holder.rvPressure.text = "$pressure hPa"
            holder.rvDayOfWeek.text = date.dayOfWeek.toString().subSequence(0,3)
            holder.rvDate.text = date.toString().replace('-', '.').subSequence(5,10)
            holder.rvWeatherType.text = weatherType
            holder.rvWeatherIcon.setImageResource(weatherIcon)
            holder.rvWindSpd.setImageResource(windSpd)
            holder.rvWindDir.setImageResource(R.drawable.ic_wi_wind_deg)
            holder.rvWindDir.rotation = windDir.toFloat()
        }
    }

    override fun getItemCount(): Int = forecastDaily.size
}
