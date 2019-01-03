package com.softaai.cityweather.data.room


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class RoomConfig {
    companion object {

        const val DATABASE_WEATHER = "city_weather.db"
        const val TABLE_CITIES= "cities"

        private const val SELECT_FROM = "SELECT * FROM "

        const val SELECT_CITIES = SELECT_FROM + TABLE_CITIES
    }
}