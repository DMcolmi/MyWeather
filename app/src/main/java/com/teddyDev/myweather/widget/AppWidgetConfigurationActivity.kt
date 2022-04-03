package com.teddyDev.myweather.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.teddyDev.myweather.R
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.databinding.ActivityAppWidgetConfigurationBinding
import com.teddyDev.myweather.listAdapter.WidgetLocationListAdapter
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModel
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModelFactory
import com.teddyDev.myweather.viewModel.LocationViewModel
import com.teddyDev.myweather.viewModel.LocationViewModelFactory

class AppWidgetConfigurationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppWidgetConfigurationBinding

    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels {
        CurrentWeatherViewModelFactory(
            (application as WeatherApplication).appDatabase.getCurrentWeatherDao(),
            application
        )
    }

    private val locationViewModel: LocationViewModel by viewModels {
        LocationViewModelFactory(
            (application as WeatherApplication).appDatabase.getLocationDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppWidgetConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = WidgetLocationListAdapter{
             updateWidgetFromConfigurationActivity(it)
        }

        locationViewModel.locationList.observe(this){
            adapter.submitList(it)
        }

        binding.apply {
            addLocationRecyclerView.adapter = adapter
        }

    }

    private fun updateWidgetFromConfigurationActivity(locationEntity: LocationEntity){
        setResult(Activity.RESULT_CANCELED)

        val appWidgetId: Int = intent?.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        bindWidgetToLocation(appWidgetId,locationEntity)

        val appWidgetManager = AppWidgetManager.getInstance(this)

        val views = RemoteViews(this.packageName, R.layout.new_app_widget)
        appWidgetManager.updateAppWidget(appWidgetId,views)

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()


    }


    private fun bindWidgetToLocation(appWidgetId: Int, locationEntity: LocationEntity){
        currentWeatherViewModel.bindCurrentWeatherEntityToWidget(appWidgetId, locationEntity)
    }
}