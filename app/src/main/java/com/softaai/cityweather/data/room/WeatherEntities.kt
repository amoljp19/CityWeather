package com.softaai.cityweather.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

@Entity(tableName = RoomConfig.TABLE_CITIES)
data class CityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var cityName: String
)