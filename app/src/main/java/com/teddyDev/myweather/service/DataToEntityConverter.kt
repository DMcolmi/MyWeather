package com.teddyDev.myweather.service

import android.R
import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.database.CurrentWeatherEntity
import java.text.SimpleDateFormat
import java.util.*

    fun fromCurrentWeatherDataToEntity(currentWeatherData: CurrentWeatherData, previousWeatherEntity: CurrentWeatherEntity): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            name = previousWeatherEntity.name,
            country = previousWeatherEntity.country,
            state = previousWeatherEntity.state,
            weatherName = currentWeatherData.name,
            weatherCountry = currentWeatherData.sys.country,
            lon = currentWeatherData.coord.lon.toString(),
            lat = currentWeatherData.coord.lat.toString(),
            windSpeed = currentWeatherData.wind?.speed,
            windDeg = currentWeatherData.wind?.deg,
            temp = currentWeatherData.main?.temp,
            feelsLike = currentWeatherData.main?.feelsLike,
            tempMin = currentWeatherData.main?.tempMin,
            tempMax = currentWeatherData.main?.tempMax,
            pressure = currentWeatherData.main?.pressure,
            humidity = currentWeatherData.main?.humidity,
            sunrise = currentWeatherData.sys?.sunrise,
            sunset = currentWeatherData.sys?.sunset,
            description = currentWeatherData.weather[0]?.description,
            icon = currentWeatherData.weather[0]?.icon,
            visibility = currentWeatherData.visibility,
            timestamp = getStringTimestamp(),
            widgetId = previousWeatherEntity.widgetId
        )
    }

    fun fromLocationDataToCurrentWeatherEntity(locationData: LocationData) : CurrentWeatherEntity{
        return  CurrentWeatherEntity(
            name = locationData.name,
            country = locationData.country,
            lon = locationData.lon,
            lat = locationData.lat,
            state = locationData.state,
            timestamp = getStringTimestamp()
        )
    }





