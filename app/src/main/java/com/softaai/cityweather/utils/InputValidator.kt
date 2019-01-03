package com.softaai.cityweather.utils

import java.util.regex.Pattern


/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

object InputValidator {
    private val VALID_CITY_REGEX = Pattern.compile("[a-zA-Z]+")

    fun isValidCityInput(city: String): Boolean {
        val matches = VALID_CITY_REGEX.matcher(city).matches()
        return matches
    }
}