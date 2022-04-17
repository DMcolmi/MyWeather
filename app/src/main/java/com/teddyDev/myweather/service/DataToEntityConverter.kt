package com.teddyDev.myweather.service

import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.api.ForecastWeatherData
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity

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

fun fromLocationDataToCurrentWeatherEntity(locationData: LocationData) : CurrentWeatherEntity {
    return  CurrentWeatherEntity(
        name = locationData.name,
        country = locationData.country,
        lon = locationData.lon,
        lat = locationData.lat,
        state = locationData.state,
        timestamp = getStringTimestamp()
    )
}

fun fromLocationDataToEntity(locationData: LocationData): LocationEntity {
    return LocationEntity(
        name = locationData.name,
        country = locationData.country,
        lon = locationData.lon ?:"",
        lat = locationData.lat ?:"",
        state = locationData.state
    )
}

fun fromForecastWeatherDataToCurrentWeatherEntity(forecastWeatherData: ForecastWeatherData, previousWeatherEntity: CurrentWeatherEntity){
    CurrentWeatherEntity(
        name = previousWeatherEntity.name,
        country = previousWeatherEntity.country,
        state = previousWeatherEntity.state,
        weatherName = previousWeatherEntity.name,
        weatherCountry = previousWeatherEntity.country,
        lon = forecastWeatherData.lon.toString(),
        lat = forecastWeatherData.lat.toString(),
        windSpeed = forecastWeatherData.current.windSpeed,
        windDeg = forecastWeatherData.current.windDeg,
        temp = forecastWeatherData.current.temp,
        feelsLike = forecastWeatherData.current.feelsLike,
        tempMin = forecastWeatherData.daily[0].temp?.min,
        tempMax = forecastWeatherData.daily[0].temp?.max,
        pressure = forecastWeatherData.current.pressure,
        humidity = forecastWeatherData.current.humidity,
        sunrise = forecastWeatherData.current.sunrise,
        sunset = forecastWeatherData.current.sunset,
        description = forecastWeatherData.current.weather[0]?.description,
        icon = forecastWeatherData.current.weather[0]?.icon,
        visibility = forecastWeatherData.current.visibility,
        timestamp = getStringTimestamp(),
        widgetId = previousWeatherEntity.widgetId
    )
}




