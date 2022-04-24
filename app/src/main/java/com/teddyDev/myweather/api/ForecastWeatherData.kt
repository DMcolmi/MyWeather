package com.teddyDev.myweather.api

data class ForecastWeatherData (

    var lat : Double?,
    var lon : Double?,
    var timezone : String?,
    var timezoneOffset : Int?,
    var current : Current = Current(),
    var hourly : List<Hourly> = arrayListOf(),
    var daily : List<Daily> = arrayListOf(),
    var alerts : List<Alerts> = arrayListOf()
)

data class Current (

    var dt : Int? = null,
    var sunrise : Long? = null,
    var sunset : Long? = null,
    var temp : Double? = null,
    var feelsLike : Double? = null,
    var pressure : Int? = null,
    var humidity : Int? = null,
    var dewPoint : Double? = null,
    var uvi : Double? = null,
    var clouds : Int? = null,
    var visibility : Int? = null,
    var windSpeed : Double? = null,
    var windDeg : Int? = null,
    var weather : List<Weather> = arrayListOf()
)

data class Hourly (

    var dt : Int? = null,
    var temp : Double? = null,
    var feelsLike : Double? = null,
    var pressure : Int? = null,
    var humidity : Int? = null,
    var dewPoint : Double? = null,
    var uvi : Double? = null,
    var clouds : Int? = null,
    var visibility : Int? = null,
    var windSpeed : Double? = null,
    var windDeg : Int? = null,
    var windQust : Double? = null,
    var weather : List<Weather>? = null,
    var pop : Double? = null,
    var rain : Rain? = null
)

data class Daily (

    var dt : Int? = null,
    var sunrise : Int? = null,
    var sunset : Int? = null,
    var moonrise : Int? = null,
    var moonset : Int? = null,
    var moonPhase : Double? = null,
    var temp : Temp? = null,
    var feelsLike : FeelsLike? = null,
    var pressure : Int? = null,
    var humidity : Int? = null,
    var dewPoint : Double? = null,
    var windSpeed : Double? = null,
    var windDeg : Int? = null,
    var windQust : Int? = null,
    var weather : List<Weather>? = null,
    var clouds : Int? = null,
    var pop : Double? = null,
    var rain : Double? = null,
    var uvi : Double? = null
)

data class Weather (

    var id : Int? = null,
    var main : String? = null,
    var description : String? = null,
    var icon : String? = null
)

data class Temp (

    var day : Double? = null,
    var min : Double? = null,
    var max : Double? = null,
    var night : Double? = null,
    var eve : Double? = null,
    var morn : Double? = null
)

data class FeelsLike (

    var day : Double? = null,
    var night : Double? = null,
    var eve : Double? = null,
    var morn : Double? = null
)

data class Rain (

    var oneHour : Double? = null
)

data class Alerts (

    var senderName : String? = null,
    var event : String? = null,
    var start : Int? = null,
    var end : Int? = null,
    var description : String? = null,
    var tags : List<String> = arrayListOf()
)