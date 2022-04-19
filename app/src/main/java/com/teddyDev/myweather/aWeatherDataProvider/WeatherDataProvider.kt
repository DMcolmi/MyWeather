package com.teddyDev.myweather.aWeatherDataProvider

import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity

interface WeatherDataProvider {

    suspend fun getLocation(cityName: String) :List<LocationEntity>

    suspend fun getCurrentWeather(lon: String, lat: String) : CurrentWeatherEntity

    suspend fun getWeatherForecast(lon: String, lat:String) : HourlyForecastWeatherEntity
}