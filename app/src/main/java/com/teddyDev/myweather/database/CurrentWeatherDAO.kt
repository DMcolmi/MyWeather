package com.teddyDev.myweather.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("select * from t_current_weather order by name asc")
    fun getAllCurrentWeather(): Flow<List<CurrentWeatherEntity>>

    @Delete
    suspend fun deleteCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("select * from t_current_weather where widget_id = :widgetId limit 1")
    fun getCurrentWeatherEntityByWidgetId(widgetId: Int): Flow<CurrentWeatherEntity>

}