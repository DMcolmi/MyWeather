package com.teddyDev.myweather.viewModel

import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.database.LocationDAO
import com.teddyDev.myweather.database.LocationEntity
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class LocationViewModel(private val locationDAO: LocationDAO): ViewModel() {

    val locationList : LiveData<List<LocationEntity>> = locationDAO.getAllLocations().asLiveData()

    var newLocation: String = ""

    private var _isApiCalFinishedWithResult: MutableLiveData<Boolean> = MutableLiveData()

    val isApiCalFinishedWithResult :LiveData<Boolean>
        get() = _isApiCalFinishedWithResult

    private var _retrievedLocationFromApi: MutableLiveData<List<LocationData>> = MutableLiveData()

    val retrievedLocationFromApi: LiveData<List<LocationData>>
        get() = _retrievedLocationFromApi

    fun saveLocation(location: LocationData){
        val locationEntity = LocationEntity(
            name = location.name ?:"",
            country = location.country ?:"",
            lat = location.lat ?:"",
            lon = location.lon ?:"",
            state = location.state
        )

        viewModelScope.launch {
            locationDAO.insertNewLocation(locationEntity)
        }
        newLocation = ""
    }

    fun searchLocation(location: String){
        viewModelScope.launch {
            OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getLocation(
                location
            ).let { locationsFromApi ->
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
        _retrievedLocationFromApi = MutableLiveData<List<LocationData>>()
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