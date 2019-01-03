package com.softaai.cityweather.weather.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.softaai.cityweather.data.repository.WeatherRepository
import javax.inject.Inject


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class WeatherViewModelFactory @Inject constructor(val weatherRepository: WeatherRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(weatherRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}