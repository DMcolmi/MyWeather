package com.teddyDev.myweather.aWeatherDataProvider

import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity
import com.teddyDev.myweather.service.fromLocationDataToEntity

class WeatherDataProviderOpenW : WeatherDataProvider {

    override suspend fun getLocation(cityName: String): List<LocationEntity> {
        var locations :List<LocationEntity> = arrayListOf()
        if(cityName.isNullOrBlank())
            return locations;

        val locationsData = OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getLocation(cityName)

        locationsData.let { locationsData
            locations = locationsData.map { fromLocationDataToEntity(it) }
        }

        return locations;
    }

    override suspend fun getCurrentWeather(lon: String, lat: String): CurrentWeatherEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getWeatherForecast(lon: String, lat: String): HourlyForecastWeatherEntity {
        TODO("Not yet implemented")
    }

}