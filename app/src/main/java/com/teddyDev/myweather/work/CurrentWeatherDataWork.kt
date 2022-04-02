package com.teddyDev.myweather.work

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.database.AppDatabase
import com.teddyDev.myweather.service.getStringTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class CurrentWeatherDataWork(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {

    private val currentWeatherDAO = (
        context.applicationContext as WeatherApplication
    ).appDatabase.getCurrentWeatherDao()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {

                currentWeatherDAO.getAllCurrentWeather().collect(){
                    it.forEach {    currentWeather ->
                        currentWeather.timestamp = getStringTimestamp()
                        currentWeatherDAO.insertOrUpdateCurrentWeather(currentWeather)
                    }
                }

                Result.success()
            } catch (e: Throwable) {
                Result.failure()
            }
        }
    }
}