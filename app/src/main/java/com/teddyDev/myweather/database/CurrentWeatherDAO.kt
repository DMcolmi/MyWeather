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

    @Query("select * from t_current_weather order by name asc")
    fun getAllCurrentWeatherForWorkManager(): List<CurrentWeatherEntity>

    @Query("select * from t_current_weather where name = :name and country = :country")
    fun getCurrentWeatherData(name: String, country: String): Flow<CurrentWeatherEntity>

    @Query("update t_current_weather set widget_id = null where widget_id = :widgetId")
    suspend fun removeWidgetIdFromEntity(widgetId: Int)
}