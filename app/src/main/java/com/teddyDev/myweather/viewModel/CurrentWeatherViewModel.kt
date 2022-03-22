package com.teddyDev.myweather.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.database.CurrentWeatherDAO
import com.teddyDev.myweather.database.CurrentWeatherEntity
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.service.fromCurrentWeatherDataToEntity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class CurrentWeatherViewModel(private val currentWeatherDAO: CurrentWeatherDAO) : ViewModel() {

    val weatherData: LiveData<List<CurrentWeatherEntity>> = currentWeatherDAO.getAllCurrentWeather().asLiveData()

    private var currentWeatherDataToUpdate: MutableLiveData<CurrentWeatherData> = MutableLiveData()

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

    fun bindCurrentWeatherEntityToWidget(widgetId:Int){
        viewModelScope.launch {
            val currentWeatherDataList = currentWeatherDAO.getAllCurrentWeather()
            currentWeatherDataList.collect(){
                val currentWeatherData = it[0]
                currentWeatherData.widgetId = widgetId
                currentWeatherDAO.insertOrUpdateCurrentWeather(currentWeatherData)
            }
        }
    }

    fun getWeatherDataFromWidgetId(widgetId: Int): LiveData<CurrentWeatherEntity> = currentWeatherDAO.getCurrentWeatherEntityByWidgetId(widgetId).asLiveData()

}

class CurrentWeatherViewModelFactory(private val currentWeatherDAO: CurrentWeatherDAO) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrentWeatherViewModel(currentWeatherDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}