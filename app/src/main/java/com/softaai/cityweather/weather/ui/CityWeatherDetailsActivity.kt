package com.softaai.cityweather.weather.ui

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.softaai.cityweather.R
import com.softaai.cityweather.data.dto.WeatherDetailsDTO
import com.softaai.cityweather.data.dto.WeeklyWeatherDTO
import com.softaai.cityweather.utils.ChartFormatter
import com.softaai.cityweather.utils.StringFormatter.convertTimestampToDayAndHourFormat
import com.softaai.cityweather.utils.StringFormatter.convertToValueWithUnit
import com.softaai.cityweather.utils.StringFormatter.unitDegreesCelsius
import com.softaai.cityweather.utils.StringFormatter.unitPercentage
import com.softaai.cityweather.utils.StringFormatter.unitsMetresPerSecond
import com.softaai.cityweather.utils.WeatherMathUtils.convertFahrenheitToCelsius
import com.softaai.cityweather.weather.adapters.WeeklyWeatherAdapter
import kotlinx.android.synthetic.main.activity_city_weather_details.*
import org.parceler.Parcels
import java.util.*


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class CityWeatherDetailsActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_weather_details)
        val weatherDetails = Parcels.unwrap<WeatherDetailsDTO>(intent.getParcelableExtra(getString(R.string.intentWeatherDetailsParcelerBundleName)))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = weatherDetails.cityName

        setupMainWeatherDetailsInfo(weatherDetails)
        setupRecyclerView(weatherDetails)
        setupHourlyChart(chartHourlyWeather, weatherDetails)

        weatherDetails.temperature?.let { setupTemperatureTextColor(it) }
    }

    private fun setupMainWeatherDetailsInfo(weatherDetails: WeatherDetailsDTO) {
        textViewCurrentTime.text = convertTimestampToDayAndHourFormat(Date().time)
        textViewCurrentTemperature.text = convertToValueWithUnit(2, unitDegreesCelsius, weatherDetails.temperature)
        textViewWeatherSummary.text = weatherDetails.weatherSummary
        textViewHumidityValue.text = convertToValueWithUnit(2, unitPercentage, weatherDetails.humidity)
        textViewWindSpeedValue.text = convertToValueWithUnit(2, unitsMetresPerSecond, weatherDetails.windSpeed)
        textViewCloudCoverageValue.text = convertToValueWithUnit(2, unitPercentage, weatherDetails.cloudsPercentage)
    }

    private fun setupRecyclerView(weatherDetails: WeatherDetailsDTO) {
        val weeklyWeatherList = weatherDetails.weeklyDayWeahterList as ArrayList<WeeklyWeatherDTO>
        val adapter: WeeklyWeatherAdapter? = WeeklyWeatherAdapter(weeklyWeatherList)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewWeeklyWeather.layoutManager = mLayoutManager
        recyclerViewWeeklyWeather.itemAnimator = DefaultItemAnimator()
        recyclerViewWeeklyWeather.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerViewWeeklyWeather.adapter = adapter
    }

    private fun setupTemperatureTextColor(temperature: Double) {
        when {
            temperature < 10 -> textViewCurrentTemperature.setTextColor(Color.BLUE)
            temperature in 10..20 -> textViewCurrentTemperature.setTextColor(Color.BLACK)
            temperature > 20 -> textViewCurrentTemperature.setTextColor(Color.RED)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupHourlyChart(lineChart: LineChart, weatherDetailsDTO: WeatherDetailsDTO) {

        val entries = ArrayList<Entry>()
        val temperatureList = ArrayList<Int>()

        for (i in 0..24) {
            convertFahrenheitToCelsius(weatherDetailsDTO.hourlyWeatherList?.get(i)?.temperature)?.let {
                temperatureList.add(it.toInt())
                entries.add(Entry(i.toFloat(), it.toFloat()))
            }
        }

        val lineDataSet = LineDataSet(entries, "Label")
        customizeLineDataSet(lineDataSet)

        val leftAxis = lineChart.axisLeft
        setYAxis(leftAxis, temperatureList)

        val rightAxis = lineChart.axisRight
        setYAxis(rightAxis, temperatureList)

        val downAxis = lineChart.xAxis
        weatherDetailsDTO.hourlyWeatherStringFormatedHoursList?.let {
            setXAxis(downAxis, weatherDetailsDTO.hourlyWeatherStringFormatedHoursList)
        }

        val lineData = LineData(lineDataSet)
        lineDataSet.valueFormatter = ChartFormatter.ValueFormatter()
        customizeLineChart(lineChart, lineData)
    }

    private fun setXAxis(xAxis: XAxis, values: ArrayList<String>) {
        xAxis.labelCount = 25
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = ChartFormatter.AxisValueFormatter(values)
    }

    private fun customizeLineDataSet(lineDataSet: LineDataSet) {
        lineDataSet.valueTextSize = 12f
        lineDataSet.circleHoleRadius = 2.5f
        lineDataSet.circleRadius = 4f
        lineDataSet.valueFormatter = ChartFormatter.ValueFormatter()
        lineDataSet.color = R.color.colorAccent
        lineDataSet.valueTextColor = R.color.colorPrimary
    }

    private fun customizeLineChart(lineChart: LineChart, lineData: LineData) {
        val description = Description()
        description.text = ""
        lineChart.data = lineData
        lineChart.legend.isEnabled = false
        lineChart.setTouchEnabled(false)
        lineChart.description = description
        lineChart.canScrollHorizontally(1)
        lineChart.invalidate()
        lineChart.notifyDataSetChanged()
    }


    private fun setYAxis(axis: YAxis, temp: ArrayList<Int>) {
        axis.setDrawGridLines(false)
        axis.setDrawLabels(false)
        axis.axisMinimum = (Collections.min(temp) - 2).toFloat()
        axis.axisMaximum = (Collections.max(temp) + 2).toFloat()
    }
}