package com.teddyDev.myweather.viewModel

import android.location.Location
import androidx.lifecycle.*
import com.teddyDev.myweather.database.LocationDAO
import com.teddyDev.myweather.database.LocationEntity
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class LocationViewModel(private val locationDAO: LocationDAO): ViewModel() {

    val locationList : LiveData<List<LocationEntity>> = locationDAO.getAllLocations().asLiveData()

    var newLocation: String = ""

    fun saveLocation(location: String){
        val location = LocationEntity(location = location)
        viewModelScope.launch {
            locationDAO.insertNewLocation(location)
        }
        newLocation = ""
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