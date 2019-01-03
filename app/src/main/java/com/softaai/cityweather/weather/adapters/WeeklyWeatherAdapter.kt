package com.softaai.cityweather.weather.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.softaai.cityweather.R
import com.softaai.cityweather.data.dto.WeeklyWeatherDTO
import com.softaai.cityweather.utils.StringFormatter.convertToValueWithUnit
import com.softaai.cityweather.utils.StringFormatter.unitDegreesCelsius
import com.softaai.cityweather.utils.WeatherMathUtils.convertFahrenheitToCelsius


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class WeeklyWeatherAdapter(val list: ArrayList<WeeklyWeatherDTO>) : RecyclerView.Adapter<WeeklyWeatherAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_city_weather_day, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.textViewDayMaxTemp.text = convertToValueWithUnit(2, unitDegreesCelsius, convertFahrenheitToCelsius(item.maxTemp.toDouble()))
        holder.textViewDayMinTemp.text = convertToValueWithUnit(2, unitDegreesCelsius, convertFahrenheitToCelsius(item.minTemp.toDouble()))
        holder.textViewDayWeatherWeekDay.text = item.dayOfWeek
        holder.textViewDayWeatherType.text = item.weatherType
    }

    class ViewHolder(view: View)  : RecyclerView.ViewHolder(view) {
        var textViewDayMaxTemp : TextView
        var textViewDayMinTemp: TextView
        var textViewDayWeatherWeekDay: TextView
        var textViewDayWeatherType: TextView


        init {
            textViewDayMaxTemp = view.findViewById(R.id.textViewDayMaxTemp)
            textViewDayMinTemp = view.findViewById(R.id.textViewDayMinTemp)
            textViewDayWeatherWeekDay = view.findViewById(R.id.textViewDayWeatherWeekDay)
            textViewDayWeatherType = view.findViewById(R.id.textViewDayWeatherType)
        }
    }
}