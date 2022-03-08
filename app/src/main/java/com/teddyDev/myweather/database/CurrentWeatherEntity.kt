package com.teddyDev.myweather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.sql.Timestamp

@Entity(tableName = "t_current_weather", primaryKeys = ["name","country"])
data class CurrentWeatherEntity (
    val name: String,
    val country: String,
    val lon :Double,
    val lat :Double,
    @ColumnInfo(name="wind_speed")
    val windSpeed :Double?,
    @ColumnInfo(name = "wind_deg")
    val windDeg :Int?,
    val temp :Double?,
    val feelsLike :Double?,
    val tempMin :Double?,
    val tempMax :Double?,
    val pressure :Int?,
    val humidity :Int?,
    val sunrise :Int?,
    val sunset :Int?,
    val description :String?,
    val icon :String?,
    val visibility :Int?,
    @ColumnInfo(defaultValue = "(CURRENT_TIMESTAMP)")
    val timestamp: String
)