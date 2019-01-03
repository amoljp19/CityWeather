package com.softaai.cityweather.di

import com.softaai.cityweather.weather.ui.CityWeatherSearchActivity
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

@Component(modules = arrayOf(AppModule::class, CityWeatherRemoteModule::class, RoomModule::class, WeatherRepositoryModule::class))
@Singleton
interface AppComponent {

    fun inject(weatherCitySearchActivity: CityWeatherSearchActivity)
}