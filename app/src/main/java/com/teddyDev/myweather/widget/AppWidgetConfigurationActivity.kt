package com.teddyDev.myweather.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.teddyDev.myweather.R
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.databinding.ActivityAppWidgetConfigurationBinding
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModel
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModelFactory
import com.teddyDev.myweather.viewModel.LocationViewModelFactory
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AppWidgetConfigurationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppWidgetConfigurationBinding

    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels {
        CurrentWeatherViewModelFactory(
            (this?.application as WeatherApplication).appDatabase.getCurrentWeatherDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppWidgetConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.widgetButtonFinishConfiguration.setOnClickListener {
            updateWidgetFromConfigurationActivity()
        }
    }

    private fun updateWidgetFromConfigurationActivity(){
        setResult(Activity.RESULT_CANCELED)

        val appWidgetId: Int = intent?.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        bindWidgetToLocation(appWidgetId)

        val appWidgetManager = AppWidgetManager.getInstance(this)

        val views = RemoteViews(this.packageName, R.layout.new_app_widget)
        appWidgetManager.updateAppWidget(appWidgetId,views)

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()


    }


    private fun bindWidgetToLocation(appWidgetId: Int){
        currentWeatherViewModel.bindCurrentWeatherEntityToWidget(appWidgetId)
    }
}