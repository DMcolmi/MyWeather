package com.teddyDev.myweather.service

import com.teddyDev.myweather.api.openWeatherCurrentWeatherData.CurrentWeatherData
import com.teddyDev.myweather.database.CurrentWeatherEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DataToEntityConverter {

    fun fromCurrentWeatherDataToEntity(currentWeatherData: CurrentWeatherData): CurrentWeatherEntity {
        return CurrentWeatherEntity(
            name = currentWeatherData.name,
            country = currentWeatherData.sys.country,
            lat = currentWeatherData.coord.lat,
            lon = currentWeatherData.coord.lon,
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
            timestamp = ""//LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        )
    }
}
