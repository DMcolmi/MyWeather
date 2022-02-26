package com.teddyDev.myweather.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {

    @Insert
    fun insertNewLocation(location: LocationEntity)

    @Query("select * from t_location order by id ASC")
    fun getAllLocations(): Flow<List<LocationEntity>>

}