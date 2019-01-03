package com.softaai.cityweather

import android.app.Application
import com.softaai.cityweather.di.*


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class CityWeatherApplication : Application()
{
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    fun initializeDagger() {

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .roomModule(RoomModule())
            .cityWeatherRemoteModule(CityWeatherRemoteModule()).build()
    }
}