package com.softaai.cityweather.data.remote.service

import com.softaai.cityweather.BuildConfig


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

class RemoteConfig {
    companion object {
        const val DARK_SKY_BASE_API_LAYER = "https://api.darksky.net/forecast/"
        const val DARK_SKY_ACCESS_KEY_API_LAYER = BuildConfig.DARK_SKY_API_KEY
        const val BASE_URL = DARK_SKY_BASE_API_LAYER + DARK_SKY_ACCESS_KEY_API_LAYER + "/"
        const val GEOCODING_BASE_URL = "https://maps.googleapis.com/maps/api/geocode/"
    }
}