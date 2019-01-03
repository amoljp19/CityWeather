package com.softaai.cityweather.data.repository

import com.softaai.cityweather.data.dto.WeatherDetailsDTO
import com.softaai.cityweather.data.room.CityEntity
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */


interface WeatherRepository {

    fun getCities(): Flowable<List<CityEntity>>

    fun getWeather(cityName: String): Single<WeatherDetailsDTO>

    fun addCity(cityName: String)
}