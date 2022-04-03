package com.teddyDev.myweather.viewModel

import android.app.Application
import androidx.lifecycle.*
import androidx.work.*
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.database.CurrentWeatherDAO
import com.teddyDev.myweather.database.CurrentWeatherEntity
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.service.fromCurrentWeatherDataToEntity
import com.teddyDev.myweather.work.CurrentWeatherDataWork
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit


class CurrentWeatherViewModel(private val currentWeatherDAO: CurrentWeatherDAO, private val application: Application) : ViewModel() {

    val weatherData: LiveData<List<CurrentWeatherEntity>> = currentWeatherDAO.getAllCurrentWeather().asLiveData()

    private var currentWeatherDataToUpdate: MutableLiveData<CurrentWeatherData> = MutableLiveData()

    private val workManager = WorkManager.getInstance(application)

    fun updateCurrentWeatherDataForThisLocation(currentWeatherEntity: CurrentWeatherEntity) {
        viewModelScope.launch {
            currentWeatherDataToUpdate.value =
                OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getCurrentWeatherData(
                    currentWeatherEntity.lat.toString() ?: "",
                    currentWeatherEntity.lon.toString() ?:""
                )

            currentWeatherDataToUpdate.value?.let {
                val currentWeatherEntity = fromCurrentWeatherDataToEntity(it,currentWeatherEntity)
                currentWeatherDAO.insertOrUpdateCurrentWeather(currentWeatherEntity)
            }
        }
    }

    fun deleteCurrentWeatherEntity(currentWeatherEntity: CurrentWeatherEntity){
        viewModelScope.launch {
            currentWeatherDAO.deleteCurrentWeather(currentWeatherEntity)
        }
    }

    fun bindCurrentWeatherEntityToWidget(widgetId:Int, locationEntity: LocationEntity){
        viewModelScope.launch {
                val currentWeatherData = currentWeatherDAO.getCurrentWeatherData(locationEntity.name, locationEntity.country)
                currentWeatherData.collect(){
                    it.widgetId = widgetId
                    currentWeatherDAO.insertOrUpdateCurrentWeather(it)
                }
        }
    }

    fun getWeatherDataFromWidgetId(widgetId: Int): LiveData<CurrentWeatherEntity> = currentWeatherDAO.getCurrentWeatherEntityByWidgetId(widgetId).asLiveData()

    fun beginUniqueWorkToUpdateCurrentWeather(){
        val constraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequestUpdateWeather = PeriodicWorkRequestBuilder<CurrentWeatherDataWork>(UPDATE_INTERVAL,TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()

        workManager.enqueueUniquePeriodicWork("UPDATE_CURRENT_WEATHER", ExistingPeriodicWorkPolicy.KEEP, workRequestUpdateWeather)
    }

    companion object {
        const  val UPDATE_INTERVAL: Long = 60
    }
}

class CurrentWeatherViewModelFactory(private val currentWeatherDAO: CurrentWeatherDAO, private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrentWeatherViewModel(currentWeatherDAO, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}