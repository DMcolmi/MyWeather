package com.teddyDev.myweather.api

import com.squareup.moshi.Json

data class LocationData(
    val name: String,
    @Json(name = "local_names")
    val localNames: LocalNames,
    val lat: String,
    val lon: String,
    val country: String,
    val state: String
)
