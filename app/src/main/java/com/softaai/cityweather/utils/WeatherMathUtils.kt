package com.softaai.cityweather.utils


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

object WeatherMathUtils {

    fun convertFahrenheitToCelsius(temperatureFahrenheit: Double?): Double? =
        if (temperatureFahrenheit != null)
            ((temperatureFahrenheit - 32) * 5) / 9
        else null
}