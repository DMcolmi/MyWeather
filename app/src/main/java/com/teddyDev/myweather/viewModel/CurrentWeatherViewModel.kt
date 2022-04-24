package com.teddyDev.myweather.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.work.*
import com.teddyDev.myweather.aWeatherDataProviderService.WeatherDataService
import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.database.dao.CurrentWeatherDAO
import com.teddyDev.myweather.database.entity.CurrentAndForecastWeather
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity
import com.teddyDev.myweather.work.CurrentWeatherDataWork
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit


class CurrentWeatherViewModel(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val application: Application,
) : ViewModel() {

    val weatherData: LiveData<List<CurrentWeatherEntity>> =
        currentWeatherDAO.getAllCurrentWeather().asLiveData()

    val currentAndHourlyForecast: LiveData<List<CurrentAndForecastWeather>> =
        currentWeatherDAO.getCurrentAndHourlyForecast().asLiveData()

    private var currentWeatherDataToUpdate: MutableLiveData<CurrentWeatherData> = MutableLiveData()

    private val workManager = WorkManager.getInstance(application)

    fun updateCurrentWeatherDataForThisLocation(currentWeatherEntity: CurrentWeatherEntity) {
        viewModelScope.launch {
            val updatedCurrentWeatherEntity = WeatherDataService.getUpdatedCurrentWeather(currentWeatherEntity)
            currentWeatherDAO.insertOrUpdateCurrentWeather(updatedCurrentWeatherEntity)

            val forecastList = WeatherDataService.getUpdatedHourlyForecast(updatedCurrentWeatherEntity)
            currentWeatherDAO.deleteOldForecast(currentWeatherEntity.name, currentWeatherEntity.country)
            currentWeatherDAO.insertForecast(forecastList)
        }
    }


    fun deleteCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity) {
        viewModelScope.launch {
            currentWeatherDAO.deleteCurrentWeather(currentWeatherEntity)
            currentWeatherDAO.deleteOldForecast(currentWeatherEntity.name, currentWeatherEntity.country)
        }
    }

    fun bindCurrentWeatherEntityToWidget(widgetId: Int, locationEntity: LocationEntity) {
        viewModelScope.launch {
            val currentWeatherData =
                currentWeatherDAO.getCurrentWeatherData(locationEntity.name, locationEntity.country)
            currentWeatherData.collect() {
                it.widgetId = widgetId
                currentWeatherDAO.insertOrUpdateCurrentWeather(it)
            }
        }
    }

    fun getWeatherDataFromWidgetId(widgetId: Int): LiveData<CurrentWeatherEntity> =
        currentWeatherDAO.getCurrentWeatherEntityByWidgetId(widgetId).asLiveData()

    fun beginUniqueWorkToUpdateCurrentWeather() {
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequestUpdateWeather =
            PeriodicWorkRequestBuilder<CurrentWeatherDataWork>(UPDATE_INTERVAL, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

        workManager.enqueueUniquePeriodicWork("UPDATE_CURRENT_WEATHER",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequestUpdateWeather)
    }

    companion object {
        const val UPDATE_INTERVAL: Long = 60
    }
}

class CurrentWeatherViewModelFactory(
    private val currentWeatherDAO: CurrentWeatherDAO,
    private val application: Application,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrentWeatherViewModel(currentWeatherDAO, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}