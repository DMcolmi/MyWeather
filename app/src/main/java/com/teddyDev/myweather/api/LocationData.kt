package com.teddyDev.myweather.api

import com.squareup.moshi.Json

data class LocationData(
    val name: String,
    @Json(name = "local_names")
    val localNames: LocalNames? = null,
    val lat: String? = null,
    val lon: String? = null,
    val country: String,
    val state: String? = null
)

data class LocalNames(
    var af          : String? = null,
    var ar          : String? = null,
    var ascii       : String? = null,
    var az          : String? = null,
    var bg          : String? = null,
    var ca          : String? = null,
    var da          : String? = null,
    var de          : String? = null,
    var el          : String? = null,
    var en          : String? = null,
    var eu          : String? = null,
    var fa          : String? = null,
    var featureName : String? = null,
    var fi          : String? = null,
    var fr          : String? = null,
    var gl          : String? = null,
    var he          : String? = null,
    var hi          : String? = null,
    var hr          : String? = null,
    var hu          : String? = null,
    var id          : String? = null,
    var it          : String? = null,
    var ja          : String? = null,
    var la          : String? = null,
    var lt          : String? = null,
    var mk          : String? = null,
    var nl          : String? = null,
    var no          : String? = null,
    var pl          : String? = null,
    var pt          : String? = null,
    var ro          : String? = null,
    var ru          : String? = null,
    var sk          : String? = null,
    var sl          : String? = null,
    var sr          : String? = null,
    var th          : String? = null,
    var tr          : String? = null,
    var vi          : String? = null,
    var zu          : String? = null
)

