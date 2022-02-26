package com.teddyDev.myweather.database

import android.location.Location
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {

    @Insert
    suspend fun insertNewLocation(location: Location)

    @Query("select * from t_location order by id ASC")
    fun getAllLocations(): Flow<List<Location>>

}