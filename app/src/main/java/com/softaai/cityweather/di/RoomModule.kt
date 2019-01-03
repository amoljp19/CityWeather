package com.softaai.cityweather.di

import android.content.Context
import com.softaai.cityweather.data.room.RoomDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

@Module
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomCurrencyDataSource(context: Context) =
        RoomDataSource.getInstance(context)
}