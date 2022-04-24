package com.teddyDev.myweather.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.teddyDev.myweather.database.dao.CurrentWeatherDAO
import com.teddyDev.myweather.database.dao.LocationDAO
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.database.entity.HourlyForecastWeatherEntity
import com.teddyDev.myweather.database.entity.LocationEntity

@Database(entities = [LocationEntity::class, CurrentWeatherEntity::class, HourlyForecastWeatherEntity::class], version = 11, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationDAO
    abstract fun getCurrentWeatherDao(): CurrentWeatherDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}