package com.teddyDev.myweather.service

import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.database.LocationEntity

fun fromLocationEntityToData(locationEntity: LocationEntity): LocationData{
    return LocationData(
        name = locationEntity.name,
        country = locationEntity.country,
        lon = locationEntity.lon,
        lat = locationEntity.lat,
        state = locationEntity.state
    )
}