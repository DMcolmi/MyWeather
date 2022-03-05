package com.teddyDev.myweather.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewLocation(location: LocationEntity)

    @Query("select * from t_location order by name ASC")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

}