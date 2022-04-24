package com.teddyDev.myweather.service

import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity

fun fromCurrentWeatherEntityToLocationEntity(currentWeatherEntity: CurrentWeatherEntity) : LocationEntity {
    return LocationEntity(
        name = currentWeatherEntity.name,
        country = currentWeatherEntity.country,
        lon = currentWeatherEntity.lon ?: "",
        lat = currentWeatherEntity.lat ?: "",
        state = currentWeatherEntity.state,
        hash = getHash(currentWeatherEntity.name,currentWeatherEntity.country)
    )
}

fun fromLocationEntityToCurrentWeatherEntity(locationEntity: LocationEntity) : CurrentWeatherEntity {
    return  CurrentWeatherEntity(
        name = locationEntity.name,
        country = locationEntity.country,
        lon = locationEntity.lon,
        lat = locationEntity.lat,
        state = locationEntity.state,
        timestamp = getStringTimestamp(),
        hash = getHash(locationEntity.name,locationEntity.country)
    )
}

fun fromLocationEntityToHourlyForecastEntity(locationEntity: LocationEntity) : HourlyForecastWeatherEntity {
    return  HourlyForecastWeatherEntity(
        name = locationEntity.name,
        country = locationEntity.country,
        lon = locationEntity.lon,
        lat = locationEntity.lat,
        state = locationEntity.state,
        hash = getHash(locationEntity.name,locationEntity.country),
        dt = 0
    )
}

