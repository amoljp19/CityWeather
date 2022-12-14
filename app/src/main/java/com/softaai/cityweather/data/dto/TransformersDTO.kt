package com.softaai.cityweather.data.dto

import com.softaai.cityweather.data.remote.model.weather.WeatherResponse
import com.softaai.cityweather.utils.StringFormatter
import com.softaai.cityweather.utils.WeatherMathUtils
import java.util.*


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */


object TransformersDTO {
    fun transformToWeatherDetailsDTO(cityName: String, weatherResponse: WeatherResponse?): WeatherDetailsDTO {
        val temperatureFahrenheit: Double? = weatherResponse?.currently?.temperature
        val temperature = WeatherMathUtils.convertFahrenheitToCelsius(temperatureFahrenheit)
        val cloudCoverPercentage: Double? = weatherResponse?.currently?.cloudCover
        val weatherSummary = weatherResponse?.currently?.summary
        val windSpeed = weatherResponse?.currently?.windSpeed
        val humidity = weatherResponse?.currently?.humidity

        val weeklyWeatherList = ArrayList<WeeklyWeatherDTO>()
        weatherResponse?.daily?.data?.forEach {
            if (it.time.toLong() * 1000 > Date().time)
                weeklyWeatherList.add(
                    WeeklyWeatherDTO(
                        it.temperatureMax.toString(),
                        it.temperatureMin.toString(),
                        StringFormatter.convertTimestampToDayOfTheWeek(it.time),
                        it.icon
                    )
                )
        }

        val hourlyWeatherList = ArrayList<HourlyWeatherDTO>()
        weatherResponse?.hourly?.data?.forEach {
            hourlyWeatherList.add(HourlyWeatherDTO(it.time.toLong(), it.temperature))
        }

        var hourlyWeatherStringFormatedHoursList = ArrayList<String>()

        if (hourlyWeatherList.size > 24) {
            hourlyWeatherStringFormatedHoursList = (0..24).mapTo(ArrayList<String>()) {
                StringFormatter.convertTimestampToHourFormat(
                    timestamp = hourlyWeatherList[it].timestamp,
                    timeZone = weatherResponse?.timezone
                )
            }
        }

        return WeatherDetailsDTO(
            cityName = cityName,
            weatherSummary = weatherSummary,
            temperature = temperature,
            windSpeed = windSpeed,
            humidity = humidity?.let { it * 100 },
            cloudsPercentage = cloudCoverPercentage?.let { it * 100 },
            weeklyDayWeahterList = weeklyWeatherList,
            hourlyWeatherList = hourlyWeatherList,
            hourlyWeatherStringFormatedHoursList = hourlyWeatherStringFormatedHoursList
        )
    }
}
