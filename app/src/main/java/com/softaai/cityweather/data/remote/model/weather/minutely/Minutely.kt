package com.softaai.cityweather.data.remote.model.weather.minutely

import com.softaai.cityweather.data.remote.model.weather.daily.Data


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

data class Minutely(
    val summary: String,
    val icon: String,
    val data: List<Data>
)

data class Data(
    val time: Int,
    val precipIntensity: Double,
    val precipProbability: Double
)