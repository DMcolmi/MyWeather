package com.teddyDev.myweather.work

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.database.AppDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


class CurrentWeatherDataWork(context: Context, workerParameters: WorkerParameters): Worker(context,workerParameters) {

    private val currentWeatherDAO = (
        context.applicationContext as WeatherApplication
    ).appDatabase.getCurrentWeatherDao()

    override fun doWork(): Result {
        return try {


            val currentWeatherData = currentWeatherDAO.getAllCurrentWeather()
            currentWeatherData.onEach {
                it.forEach {

                }
            }


            Result.success()
        } catch (e: Throwable){
            Result.failure()
        }
    }
}