package com.softaai.cityweather.di

import com.softaai.cityweather.data.repository.WeatherRepository
import com.softaai.cityweather.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */


@Module
abstract class WeatherRepositoryModule {
    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
