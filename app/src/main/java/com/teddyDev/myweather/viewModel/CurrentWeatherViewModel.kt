package com.teddyDev.myweather.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.api.CurrentWeatherData
import com.teddyDev.myweather.database.CurrentWeatherDAO
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.service.fromCurrentWeatherDataToEntity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class CurrentWeatherViewModel(private val currentWeatherDAO: CurrentWeatherDAO) : ViewModel() {

    private var _currentWeatherDataToUpdate: MutableLiveData<CurrentWeatherData> = MutableLiveData()
    val currentWeatherDataToUpdate: LiveData<CurrentWeatherData>
        get() = _currentWeatherDataToUpdate

    fun updateCurrentWeatherDataForThisLocation(location: LocationEntity) {
        viewModelScope.launch {
            _currentWeatherDataToUpdate.value =
                OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getCurrentWeatherData(
                    location.lat,
                    location.lon
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