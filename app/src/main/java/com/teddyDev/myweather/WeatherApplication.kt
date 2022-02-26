package com.teddyDev.myweather

import android.app.Application
import com.teddyDev.myweather.database.AppDatabase

class WeatherApplication: Application() {

    val appDatabase: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }

}