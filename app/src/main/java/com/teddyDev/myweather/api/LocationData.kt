package com.teddyDev.myweather.api

import com.squareup.moshi.Json

data class LocationData(
    val name: String? = null,
    @Json(name = "local_names")
    val localNames: LocalNames? = null,
    val lat: String? = null,
    val lon: String? = null,
    val country: String? = null,
    val state: String? = null
)
