package com.teddyDev.myweather.database.entity

import androidx.lifecycle.MutableLiveData
import androidx.room.Embedded
import androidx.room.Relation

data class LocationAndWeather(
    @Embedded
    val location: LocationEntity,
    @Relation(
        entity = CurrentWeatherEntity::class,
        parentColumn = "hash",
        entityColumn = "hash"
    )
    val currentAndForecastWeather: CurrentAndForecastWeather,
)