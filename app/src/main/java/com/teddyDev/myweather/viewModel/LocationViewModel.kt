package com.teddyDev.myweather.viewModel

import androidx.lifecycle.*
import com.teddyDev.myweather.aWeatherDataProvider.WeatherDataProvider
import com.teddyDev.myweather.aWeatherDataProviderService.WeatherDataService
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.database.dao.LocationDAO
import com.teddyDev.myweather.database.entity.LocationEntity
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class LocationViewModel(private val locationDAO: LocationDAO): ViewModel() {

    val locationList : LiveData<List<LocationEntity>> = locationDAO.getAllLocations().asLiveData()

    var newLocation: String = ""

    private var _isApiCalFinishedWithResult: MutableLiveData<Boolean> = MutableLiveData()

    val isApiCalFinishedWithResult :LiveData<Boolean>
        get() = _isApiCalFinishedWithResult

    private var _retrievedLocationFromApi: MutableLiveData<List<LocationEntity>> = MutableLiveData()

    val retrievedLocationFromApi: LiveData<List<LocationEntity>>
        get() = _retrievedLocationFromApi



    fun saveLocation(location: LocationEntity){
        viewModelScope.launch {
            locationDAO.insertNewLocation(location)
        }
        newLocation = ""
    }

    fun searchLocation(location: String){
        viewModelScope.launch {
            WeatherDataService.getLocation(location).let { locationsFromApi ->
                if(locationsFromApi.isNotEmpty()){
                    _retrievedLocationFromApi.value = locationsFromApi
                    _isApiCalFinishedWithResult.value = true
                }
            }
        }
    }

    fun deleteLocation(locationEntity: LocationEntity){
        viewModelScope.launch {
            locationDAO.deleteLocation(locationEntity)
        }
    }

    fun clearFieldsToSearchNewLocation(){
        newLocation = ""
        _retrievedLocationFromApi = MutableLiveData<List<LocationEntity>>()
        _isApiCalFinishedWithResult.value = false
    }
}

class LocationViewModelFactory(private val locationDAO: LocationDAO): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LocationViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(locationDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}