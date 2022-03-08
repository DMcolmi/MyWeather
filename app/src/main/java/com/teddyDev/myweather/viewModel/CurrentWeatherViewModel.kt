package com.teddyDev.myweather.viewModel

import androidx.lifecycle.*
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.api.openWeatherCurrentWeatherData.CurrentWeatherData
import com.teddyDev.myweather.database.LocationEntity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class CurrentWeatherViewModel: ViewModel() {

    private var _currentWeatherDataToUpdate: MutableLiveData<CurrentWeatherData> = MutableLiveData()
    val currentWeatherDataToUpdate: LiveData<CurrentWeatherData>
        get() = _currentWeatherDataToUpdate

    fun updateCurrentWeatherDataForThisLocation(location: LocationEntity){
        viewModelScope.launch {
            _currentWeatherDataToUpdate.value = OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getCurrentWeatherData(
                location.lat,
                location.lon
            )
        }

    }


}

class CurrentWeatherViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CurrentWeatherViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}