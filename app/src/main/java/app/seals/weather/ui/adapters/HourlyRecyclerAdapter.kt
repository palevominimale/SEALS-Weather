package app.seals.weather.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.seals.weather.R
import app.seals.weather.data.models.ForecastItemDataModel
import java.time.Instant
import java.time.ZoneId

class HourlyRecyclerAdapter(private val forecastDaily: MutableList<ForecastItemDataModel>) :
    RecyclerView.Adapter<HourlyRecyclerAdapter.ViewHolder>() {

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val rvTemp: TextView = item.findViewById(R.id.forecastHourlyItemTemp)
        val rvHumidity: TextView = item.findViewById(R.id.forecastHourlyItemHumidity)
        val rvPressure: TextView = item.findViewById(R.id.forecastHourlyItemPressure)
        val rvDayOfWeek: TextView = item.findViewById(R.id.forecastHourlyItemDayOfWeek)
        val rvDate: TextView = item.findViewById(R.id.forecastHourlyItemDate)
        val rvWeatherType: TextView = item.findViewById(R.id.forecastHourlyItemWeatherType)
        val rvWeatherIcon: ImageView = item.findViewById(R.id.forecastHourlyItemWeatherIcon)
        val rvWindSpd: ImageView = item.findViewById(R.id.forecastHourlyItemWindSpd)
        val rvWindDir: ImageView = item.findViewById(R.id.forecastHourlyItemWindDir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item_hourly, parent, false)
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        forecastDaily[position].run {
            val date = Instant.ofEpochSecond(time ?: 0L).atZone(ZoneId.systemDefault())
            holder.rvTemp.text = "$temp°C"
            holder.rvHumidity.text = "RH $humidity%"
            holder.rvPressure.text = "$pressure hPa"
            holder.rvDayOfWeek.text = date.dayOfWeek.toString().subSequence(0,3)
            holder.rvDate.text = "08.14"
            holder.rvWeatherType.text = weatherType
            holder.rvWeatherIcon.setImageResource(weatherIcon ?: R.drawable.wi_meteor)
            holder.rvWindSpd.setImageResource(windSpd ?: R.drawable.wi_wind_beaufort_0)
            holder.rvWindDir.setImageResource(R.drawable.ic_wi_wind_deg)
            holder.rvWindDir.rotation = windDir?.toFloat() ?: 0.0F
            holder.rvDate.text = date.toString().subSequence(11,16)
        }
    }

    override fun getItemCount(): Int = forecastDaily.size
}
