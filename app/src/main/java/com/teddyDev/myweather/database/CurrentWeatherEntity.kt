package com.teddyDev.myweather.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.sql.Timestamp

@Entity(tableName = "t_current_weather", primaryKeys = ["name","country"])
data class CurrentWeatherEntity (
    val name: String,
    val country: String,
    val state: String?= null,
    val lon :String?,
    val lat :String?,
    val weatherName: String?= null,
    val weatherCountry: String?= null,
    @ColumnInfo(name="wind_speed")
    val windSpeed :Double?= null,
    @ColumnInfo(name = "wind_deg")
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
    val timestamp: String,
    @ColumnInfo(name = "widget_id")
    var widgetId: Int? = null
)