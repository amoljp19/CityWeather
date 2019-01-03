package com.softaai.cityweather.di

import android.content.Context
import com.softaai.cityweather.CityWeatherApplication
import com.softaai.cityweather.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

@Module
class AppModule(private val weatherApplication: CityWeatherApplication) {

    @Provides
    @Singleton
    fun provideContext(): Context = weatherApplication
    
}