package com.teddyDev.myweather.service

import com.teddyDev.myweather.database.CurrentWeatherEntity
import com.teddyDev.myweather.database.LocationEntity

fun fromCurrentWeatherEntityToLocationEntity(currentWeatherEntity: CurrentWeatherEntity) :LocationEntity{
    return LocationEntity(
        name = currentWeatherEntity.name,
        country = currentWeatherEntity.country,
        lon = currentWeatherEntity.lon ?: "",
        lat = currentWeatherEntity.lat ?: "",
        state = currentWeatherEntity.state
    )
}