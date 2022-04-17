package com.teddyDev.myweather.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CurrentAndForecastWeather(
    @Embedded
    val currentWeather: CurrentWeatherEntity,
    @Relation(parentColumn = "hash",  entityColumn = "hash")
    val hourlyForecast: List<HourlyForecastWeatherEntity>
)