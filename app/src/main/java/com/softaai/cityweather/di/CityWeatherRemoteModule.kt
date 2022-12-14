package com.softaai.cityweather.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.softaai.cityweather.data.remote.service.RemoteConfig
import com.softaai.cityweather.data.remote.service.location.RemoteGeocodingService
import com.softaai.cityweather.data.remote.service.weather.RemoteCityWeatherService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

@Module
class CityWeatherRemoteModule
{

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()


    @Provides
    @Singleton
    @Named("WEATHER_FORECAST_API")
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    @Named("GEOCODING_API")
    fun provideWeatherForecastAPIRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(RemoteConfig.GEOCODING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideRemoteWeatherService(@Named("WEATHER_FORECAST_API") retrofit: Retrofit): RemoteCityWeatherService =
        retrofit.create(RemoteCityWeatherService::class.java)

    @Provides
    @Singleton
    fun provideRemoteGeocodingService(@Named("GEOCODING_API") retrofit: Retrofit): RemoteGeocodingService =
        retrofit.create(RemoteGeocodingService::class.java)

}