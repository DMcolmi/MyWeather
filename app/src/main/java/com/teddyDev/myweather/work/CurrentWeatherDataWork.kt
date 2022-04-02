package com.teddyDev.myweather.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.api.OpenWeatherApiService
import com.teddyDev.myweather.service.fromCurrentWeatherDataToEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CurrentWeatherDataWork(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {

    private val currentWeatherDAO = (
        context.applicationContext as WeatherApplication
    ).appDatabase.getCurrentWeatherDao()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {

                currentWeatherDAO.getAllCurrentWeatherForWorkManager().forEach {    currentWeatherToBeUpdated ->
                        val currentWeatherDataFromApi = OpenWeatherApiService.OpenWeatherApi.openWeatherApiService.getCurrentWeatherData(
                            currentWeatherToBeUpdated.lat.toString() ?: "",
                            currentWeatherToBeUpdated.lon.toString() ?:""
                        )

                        currentWeatherDataFromApi.let {
                            val currentWeatherEntity = fromCurrentWeatherDataToEntity(it,currentWeatherToBeUpdated)
                            currentWeatherDAO.insertOrUpdateCurrentWeather(currentWeatherEntity)
                        }
                    }

                Result.success()
            } catch (e: Throwable) {
                Result.failure()
            }
        }
    }
}