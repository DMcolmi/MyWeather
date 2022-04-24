package com.teddyDev.myweather.database.dao

import androidx.room.*
import com.teddyDev.myweather.database.entity.CurrentAndForecastWeather
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationAndWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("select * from t_current_weather order by name asc")
    fun getAllCurrentWeather(): Flow<List<CurrentWeatherEntity>>

    @Delete
    suspend fun deleteCurrentWeather(currentWeatherEntity: CurrentWeatherEntity)

    @Query("select * from t_current_weather where widgetId = :widgetId limit 1")
    fun getCurrentWeatherEntityByWidgetId(widgetId: Int): Flow<CurrentWeatherEntity>

    @Query("select * from t_current_weather order by name asc")
    fun getAllCurrentWeatherForWorkManager(): List<CurrentWeatherEntity>

    @Query("select * from t_current_weather where name = :name and country = :country")
    fun getCurrentWeatherData(name: String, country: String): Flow<CurrentWeatherEntity>

    @Query("update t_current_weather set widgetId = null where widgetId = :widgetId")
    suspend fun removeWidgetIdFromEntity(widgetId: Int)

    @Transaction
    @Query("select * from t_current_weather")
    fun getCurrentAndHourlyForecast(): Flow<List<CurrentAndForecastWeather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: List<HourlyForecastWeatherEntity>)

    @Query("delete from t_hourly_forecast where name = :name and country = :country")
    suspend fun deleteOldForecast(name: String, country: String)


}