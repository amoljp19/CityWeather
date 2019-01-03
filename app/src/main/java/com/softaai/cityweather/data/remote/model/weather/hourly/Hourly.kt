package com.softaai.cityweather.data.remote.model.weather.hourly

import com.softaai.cityweather.data.remote.model.weather.daily.Data


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

data class Hourly(
    val summary: String,
    val icon: String,
    val data: List<Data>
)

data class Data(
    val time: Int,
    val summary: String,
    val icon: String,
    val precipIntensity: Double,
    val precipProbability: Double,
    val temperature: Double,
    val apparentTemperature: Double,
    val dewPoint: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windGust: Double,
    val windBearing: Int,
    val cloudCover: Double,
    val uvIndex: Int,
    val visibility: Double,
    val ozone: Double
)