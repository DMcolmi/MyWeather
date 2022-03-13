package com.teddyDev.myweather.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import com.teddyDev.myweather.R
import com.teddyDev.myweather.databinding.ActivityAppWidgetConfigurationBinding

class AppWidgetConfigurationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppWidgetConfigurationBinding

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

        val appWidgetId = intent?.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        //configuration hear

        val appWidgetManager = AppWidgetManager.getInstance(this)

        val views = RemoteViews(this.packageName, R.layout.new_app_widget)
        appWidgetManager.updateAppWidget(appWidgetId,views)

        val resultValue = Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(Activity.RESULT_OK, resultValue)
        finish()
    }
}