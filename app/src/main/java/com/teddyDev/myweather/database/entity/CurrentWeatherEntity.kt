package com.teddyDev.myweather.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "t_current_weather", primaryKeys = ["name","country"])
data class CurrentWeatherEntity (
    val name: String,
    val country: String,
    val state: String?= null,
    val lon :String?,
    val lat :String?,
    val weatherName: String?= null,
    val weatherCountry: String?= null,
    val windSpeed :Double?= null,
    val windDeg :Int?= null,
    val temp :Double?= null,
    val feelsLike :Double?= null,
    val tempMin :Double?= null,
    val tempMax :Double?= null,
    val pressure :Int?= null,
    val humidity :Int?= null,
    val sunrise :Long?= null,
    val sunset :Long?= null,
    val description :String?= null,
    val icon :String?= null,
    val visibility :Int? = null,
    @ColumnInfo(defaultValue = "(CURRENT_TIMESTAMP)")
    var timestamp: String,
    var widgetId: Int? = null,
    var hash: Int
)