package com.softaai.cityweather.data.remote.service.weather

import com.softaai.cityweather.data.remote.model.weather.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

interface RemoteCityWeatherService {

    @GET("{latitude},{longitude}")
    fun requestWeatherForCity(
        @Path("latitude") latitude: String,
        @Path("longitude") longitude: String
    ): Single<WeatherResponse>
}