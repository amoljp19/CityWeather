package com.softaai.cityweather.weather.viewmodel

import android.arch.lifecycle.ViewModel
import com.softaai.cityweather.data.repository.WeatherRepository
import javax.inject.Inject


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class WeatherViewModel @Inject constructor(val weatherRepository: WeatherRepository) : ViewModel() {

    fun getWeather(cityName: String) = weatherRepository.getWeather(cityName)

    fun getCities() = weatherRepository.getCities()

    fun addCity(cityName: String) = weatherRepository.addCity(cityName)

}