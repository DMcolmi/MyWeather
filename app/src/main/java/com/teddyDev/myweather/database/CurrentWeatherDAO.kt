package com.teddyDev.myweather.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("select * from t_current_weather")
    fun getAllCurrentWeather(): Flow<List<CurrentWeatherEntity>>

    @Delete
    suspend fun deleteCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

}