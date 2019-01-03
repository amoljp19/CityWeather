package com.softaai.cityweather.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */


@Database(entities = arrayOf(CityEntity::class), version = 1, exportSchema = false)
abstract class RoomDataSource : RoomDatabase() {

    abstract fun weatherSearchCityDao(): WeatherCitiesDao

    companion object {

        @Volatile private var INSTANCE: RoomDataSource? = null

        fun getInstance(context: Context): RoomDataSource =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RoomDataSource::class.java, RoomConfig.DATABASE_WEATHER)
                .build()
    }
}