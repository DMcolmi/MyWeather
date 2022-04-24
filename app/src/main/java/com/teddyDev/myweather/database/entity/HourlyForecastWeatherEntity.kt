package com.teddyDev.myweather.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "t_hourly_forecast", primaryKeys = ["name","country","dt"])
class HourlyForecastWeatherEntity (
    //fields for mapping with other tables
    val name: String,
    val country: String,
    val state: String?= null,
    val lon :String?,
    val lat :String?,
    val hash: Int,

    //fields for hourly forecast
    val dt : Int,
    val temp : Double? = null,
    val feelsLike : Double? = null,
    val pressure : Int? = null,
    val humidity : Int? = null,
    val dewPoint : Double? = null,
    val uvi : Double? = null,
    val clouds : Int? = null,
    val visibility : Int? = null,
    val windSpeed : Double? = null,
    val windDeg : Int? = null,
    val windQust : Double? = null,
    val pop : Double? = null,

    //weather
    val id : Int? = null,
    val main : String? = null,
    val description : String? = null,
    val icon : String? = null,

    //rain
    val oneHour : Double? = null
)
