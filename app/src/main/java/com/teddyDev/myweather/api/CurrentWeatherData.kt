package com.teddyDev.myweather.api.openWeatherCurrentWeatherData

data class CurrentWeatherData (
    var coord      : Coord             = Coord(),
    var weather    : ArrayList<Weather> = arrayListOf(),
    var base       : String?            = null,
    var main       : Main?              = Main(),
    var visibility : Int?               = null,
    var wind       : Wind?              = Wind(),
    var clouds     : Clouds?            = Clouds(),
    var dt         : Int?               = null,
    var sys        : Sys                = Sys(),
    var timezone   : Int?               = null,
    var id         : Int?               = null,
    var name       : String,
    var cod        : Int?               = null
)

data class Weather (
    var id          : Int?    = null,
    var main        : String? = null,
    var description : String? = null,
    var icon        : String? = null
)

data class Sys (
    var type    : Int?    = null,
    var id      : Int?    = null,
    var message : Double? = null,
    var country : String =  "",
    var sunrise : Int?    = null,
    var sunset  : Int?    = null
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
