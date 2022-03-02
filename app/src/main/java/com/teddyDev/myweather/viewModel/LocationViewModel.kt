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

    lateinit var retrievedLocationFromApi: LocationData

    fun saveLocation(location: String){
        val locationEntity = LocationEntity(location = location)
        viewModelScope.launch {
            locationDAO.insertNewLocation(locationEntity)
        }
        searchLocation(location = location)
        newLocation = ""
    }

    fun searchLocation(location: String){
        viewModelScope.launch {
            OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getLocation(
                location
            ).let { locationsFromApi ->
                retrievedLocationFromApi = locationsFromApi[0]
            }
            Log.i("LocationViewModel",retrievedLocationFromApi.name)
        }
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