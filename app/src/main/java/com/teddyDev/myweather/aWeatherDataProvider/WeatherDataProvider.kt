package com.teddyDev.myweather.aWeatherDataProvider

import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.api.ForecastWeatherData
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity

interface WeatherDataProvider {

    suspend fun getLocation(cityName: String) : List<LocationData>

    suspend fun getCurrentWeather(lon: String, lat: String) : CurrentWeatherData

    suspend fun getWeatherForecast(lon: String, lat:String) : ForecastWeatherData
}