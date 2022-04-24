package com.teddyDev.myweather.aWeatherDataProvider

import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.api.ForecastWeatherData
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity
import com.teddyDev.myweather.service.fromCurrentWeatherDataToEntity2
import com.teddyDev.myweather.service.fromForecastWeatherDataToEntity
import com.teddyDev.myweather.service.fromLocationDataToEntity

object WeatherDataProviderOpenWeather : WeatherDataProvider {

    override suspend fun getLocation(cityName: String): List<LocationData> {
        return OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getLocation(cityName)
    }

    override suspend fun getCurrentWeather(lat: String, lon: String): CurrentWeatherData {
        return OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getCurrentWeatherData(lat,lon)
    }

    override suspend fun getWeatherForecast(lat: String, lon: String): ForecastWeatherData {
        return OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getForecastWeatherData(lat,lon)
    }

}