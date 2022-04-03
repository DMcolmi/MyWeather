package com.teddyDev.myweather.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.teddyDev.myweather.R
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.database.CurrentWeatherDAO
import com.teddyDev.myweather.service.getHyphenIfDoubleNull
import com.teddyDev.myweather.service.getTimeFromMilliseconds
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Implementation of App Widget functionality.
 */

class NewAppWidget : AppWidgetProvider() {

    private lateinit var currentWeatherDao: CurrentWeatherDAO

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        currentWeatherDao = (context.applicationContext as WeatherApplication).appDatabase.getCurrentWeatherDao()
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {

    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {


        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)

        CoroutineScope(Dispatchers.Main).launch {
            val currentWeatherEntity = currentWeatherDao.getCurrentWeatherEntityByWidgetId(appWidgetId)
            currentWeatherEntity.collect(){
                it?.let {
                    views.setTextViewText(R.id.widget_location_name, it.name)
                    views.setTextViewText(R.id.widget_weather_description, it.description?.uppercase())
                    views.setTextViewText(R.id.widget_weather_temperature, context.getString(R.string.weather_temperature,it.temp))
                    views.setTextViewText(R.id.widget_weather_max_min_temperature, context.getString(R.string.weather_temp_min_max
                        , getHyphenIfDoubleNull(it.tempMin)
                        , getHyphenIfDoubleNull(it.tempMax)
                    ))
                    views.setTextViewText(R.id.widget_weather_wind_speed, context.getString(R.string.weather_wind_speed,it.windSpeed))
                    views.setTextViewText(R.id.widget_weather_pressure, context.getString(R.string.weather_pressure,it.pressure))
                    views.setTextViewText(R.id.widget_weather_humidity,context.getString(R.string.weather_humidity,it.humidity))
                    views.setTextViewText(R.id.widget_sunrise_sunset, context.getString(R.string.weather_sunrise_sunset
                        , getTimeFromMilliseconds(it.sunrise)
                        , getTimeFromMilliseconds(it.sunset)
                    ))
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
            }
        }
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)

        currentWeatherDao = (context?.applicationContext as WeatherApplication).appDatabase.getCurrentWeatherDao()
        CoroutineScope(Dispatchers.Main).launch {
            appWidgetIds?.forEach {
                currentWeatherDao.removeWidgetIdFromEntity(it)
            }
        }
    }
}

