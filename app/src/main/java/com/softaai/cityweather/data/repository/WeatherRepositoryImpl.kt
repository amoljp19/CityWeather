package com.softaai.cityweather.data.repository

import com.softaai.cityweather.data.dto.TransformersDTO
import com.softaai.cityweather.data.dto.WeatherDetailsDTO
import com.softaai.cityweather.data.remote.RemoteWeatherDataSource
import com.softaai.cityweather.data.remote.model.location.LocationResponse
import com.softaai.cityweather.data.remote.model.weather.WeatherResponse
import com.softaai.cityweather.data.room.CityEntity
import com.softaai.cityweather.data.room.RoomDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */


@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val remoteWeatherDataSource: RemoteWeatherDataSource,
    private val roomDataSource: RoomDataSource
) : WeatherRepository {

    override fun getWeather(cityName: String): Single<WeatherDetailsDTO> {

        return remoteWeatherDataSource.requestCityAddressByName(cityName)
            .flatMap({ locationResponse: LocationResponse ->
                remoteWeatherDataSource.requestWeatherForCity(
                    locationResponse.results[0].geometry.location.lat.toString(),
                    locationResponse.results[0].geometry.location.lng.toString()
                )
                    .map { weatherResponse: WeatherResponse ->
                        TransformersDTO.transformToWeatherDetailsDTO(
                            locationResponse.results[0].formatted_address,
                            weatherResponse
                        )
                    }
            })
            .retry(1)

    }


    override fun getCities(): Flowable<List<CityEntity>> {
        return roomDataSource.weatherSearchCityDao().getAllCities()
    }

    override fun addCity(cityName: String) {
        Completable.fromCallable { roomDataSource.weatherSearchCityDao().insertCity(CityEntity(cityName = cityName)) }.subscribeOn(
            Schedulers.io()).subscribe()
    }

}
