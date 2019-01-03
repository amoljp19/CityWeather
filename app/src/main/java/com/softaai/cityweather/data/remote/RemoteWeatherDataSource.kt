package com.softaai.cityweather.data.remote

import com.softaai.cityweather.data.remote.model.location.LocationResponse
import com.softaai.cityweather.data.remote.model.weather.WeatherResponse
import com.softaai.cityweather.data.remote.service.location.RemoteGeocodingService
import com.softaai.cityweather.data.remote.service.weather.RemoteCityWeatherService
import io.reactivex.Single
import javax.inject.Inject


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class RemoteWeatherDataSource @Inject constructor(private val remoteWeatherService: RemoteCityWeatherService, private val remoteGeocodingService: RemoteGeocodingService) {

    fun requestWeatherForCity(latitude: String, longitude: String): Single<WeatherResponse> =
        remoteWeatherService.requestWeatherForCity(latitude, longitude)

    fun requestCityAddressByName(cityName: String): Single<LocationResponse> = remoteGeocodingService.requestCityAddressByName(cityName)
}