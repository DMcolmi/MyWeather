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
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class CurrentWeatherViewModel(private val currentWeatherDAO: CurrentWeatherDAO) : ViewModel() {

    val weatherData: LiveData<List<CurrentWeatherEntity>> = currentWeatherDAO.getAllCurrentWeather().asLiveData()

    private var currentWeatherDataToUpdate: MutableLiveData<CurrentWeatherData> = MutableLiveData()

    fun updateCurrentWeatherDataForThisLocation(location: LocationData) {
        viewModelScope.launch {
            currentWeatherDataToUpdate.value =
                OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getCurrentWeatherData(
                    location.lat ?: "",
                    location.lon ?:""
                )
            Log.i(
                "CurrentWeatherViewModel",
                currentWeatherDataToUpdate.value?.main?.temp.toString()
            )
            currentWeatherDataToUpdate.value?.let {
                val currentWeatherEntity = fromCurrentWeatherDataToEntity(it)
                currentWeatherDAO.insertOrUpdateCurrentWeather(currentWeatherEntity)
            }
        }
    }

    fun deleteCurrentWeatherData(currentWeatherEntity: CurrentWeatherEntity){
        viewModelScope.launch {
            currentWeatherDAO.deleteCurrentWeather(currentWeatherEntity)
        }
    }


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