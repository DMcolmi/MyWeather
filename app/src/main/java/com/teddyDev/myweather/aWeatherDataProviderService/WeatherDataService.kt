package com.teddyDev.myweather.aWeatherDataProviderService

import com.teddyDev.myweather.aWeatherDataProvider.WeatherDataProviderOpenWeather
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity
import com.teddyDev.myweather.service.fromCurrentWeatherDataToEntityOld
import com.teddyDev.myweather.service.fromForecastWeatherDataToEntity
import com.teddyDev.myweather.service.fromLocationDataToEntity

object WeatherDataService {

    suspend fun getLocation(cityName: String): List<LocationEntity>{
        var locations :List<LocationEntity> = arrayListOf()
        if(cityName.isNullOrBlank())
            return locations;
        val locationsData = WeatherDataProviderOpenWeather.getLocation(cityName)

        locationsData.let { locationsData
            locations = locationsData.map { fromLocationDataToEntity(it) }
        }
        return locations;
    }

    suspend fun getUpdatedCurrentWeather(currentWeatherEntity: CurrentWeatherEntity) : CurrentWeatherEntity{

        val currentWeatherData = WeatherDataProviderOpenWeather.getCurrentWeather(
            currentWeatherEntity.lat ?: "",
            currentWeatherEntity.lon ?: "")

        return fromCurrentWeatherDataToEntityOld(currentWeatherData,currentWeatherEntity)
    }

    suspend fun getUpdatedHourlyForecast(currentWeatherEntity: CurrentWeatherEntity) : List<HourlyForecastWeatherEntity>{
        val forecastWeatherData = WeatherDataProviderOpenWeather.getWeatherForecast(
            currentWeatherEntity.lat ?: "",
            currentWeatherEntity.lon ?: "")

        val returnList = forecastWeatherData.hourly.map { fromForecastWeatherDataToEntity(it, currentWeatherEntity) }
        return returnList
    }
}