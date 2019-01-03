package com.softaai.cityweather.data.remote.service.location

import com.softaai.cityweather.data.remote.model.location.LocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

interface RemoteGeocodingService {

    @GET("json")
    fun requestCityAddressByName(
        @Query("address") address: String
    ): Single<LocationResponse>
}