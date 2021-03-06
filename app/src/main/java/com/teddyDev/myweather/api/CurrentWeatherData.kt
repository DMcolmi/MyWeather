package com.teddyDev.myweather.api

data class CurrentWeatherData (
    var coord      : Coord = Coord(),
    var weather    : List<Weather> = arrayListOf(),
    var base       : String?            = null,
    var main       : Main?              = Main(),
    var visibility : Int?               = null,
    var wind       : Wind?              = Wind(),
    var clouds     : Clouds?            = Clouds(),
    var dt         : Int?               = null,
    var sys        : Sys = Sys(),
    var timezone   : Int?               = null,
    var id         : Int?               = null,
    var name       : String,
    var cod        : Int?               = null
)

data class Sys (
    var type    : Int?    = null,
    var id      : Int?    = null,
    var message : Double? = null,
    var country : String =  "",
    var sunrise : Long?    = null,
    var sunset  : Long?    = null
)

data class Clouds (
    var all : Int? = null
)

data class Wind (
    var speed : Double? = null,
    var deg   : Int?    = null
)

data class Main (
    var temp      : Double? = null,
    var feelsLike : Double? = null,
    var tempMin   : Double? = null,
    var tempMax   : Double? = null,
    var pressure  : Int?    = null,
    var humidity  : Int?    = null
)

data class Coord (
    var lon : Double = 0.0,
    var lat : Double = 0.0
)
