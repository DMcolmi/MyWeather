package com.teddyDev.myweather.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.R
import com.teddyDev.myweather.database.entity.CurrentAndForecastWeather
import com.teddyDev.myweather.database.entity.CurrentWeatherEntity
import com.teddyDev.myweather.databinding.*
import com.teddyDev.myweather.service.getHyphenIfDoubleNull
import com.teddyDev.myweather.service.getTimeFromMilliseconds
import kotlinx.coroutines.launch

class CurrentAndForecastWeatherListAdapter(
    private val deleteLocationLambda: (CurrentWeatherEntity) -> Unit,
    private val updateLocationWeather: (CurrentWeatherEntity) -> Unit,
) : ListAdapter<CurrentAndForecastWeather, CurrentAndForecastWeatherListAdapter.LocationViewHolder>(
    DiffCallback
) {

    class LocationViewHolder(private var binding: WeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            onClickDeleteLocationLambda: (CurrentWeatherEntity) -> Unit,
            updateLocationWeather: (CurrentWeatherEntity) -> Unit,
            currentAndForecastWeather: CurrentAndForecastWeather,
        ) {

            val currentWeather = currentAndForecastWeather.currentWeather
            val forecast = currentAndForecastWeather.hourlyForecast

            binding.apply {
                locationName.text =
                    currentWeather.name + " - " + currentWeather.country + " - " + currentWeather.state

                deleteButton.setOnClickListener {
                    onClickDeleteLocationLambda(currentWeather)
                }
                refreshWeatherButton.setOnClickListener {
                    updateLocationWeather(currentWeather)
                }
                weatherDescription.text = currentWeather.description?.uppercase()
                weatherTemperature.text =
                    root.context.getString(R.string.weather_temperature, currentWeather.temp)
                weatherSunriseSunset.text = root.context.getString(R.string.weather_sunrise_sunset,
                    getTimeFromMilliseconds(currentWeather.sunrise),
                    getTimeFromMilliseconds(currentWeather.sunset)
                )
                weatherMaxMinTemperature.text =
                    root.context.getString(R.string.weather_temp_min_max,
                        getHyphenIfDoubleNull(currentWeather.tempMin),
                        getHyphenIfDoubleNull(currentWeather.tempMax)
                    )
                weatherWindSpeed.text =
                    root.context.getString(R.string.weather_wind_speed, currentWeather.windSpeed)
                weatherPressure.text =
                    root.context.getString(R.string.weather_pressure, currentWeather.pressure)
                weatherHumidity.text =
                    root.context.getString(R.string.weather_humidity, currentWeather.humidity)

                weatherGraph.setContent {
                    val scrollState = rememberLazyListState()
                    val coroutineScope = rememberCoroutineScope()
                    var index = 0

                    fun increaseIndex(){
                        if (index + 5 < 48)
                            index += 5
                    }

                    MaterialTheme {
                        Row(modifier = Modifier
                            .height(200.dp)
                            .padding(16.dp)) {
                            Column() {
                                OutlinedButton(  onClick = {
                                    coroutineScope.launch {
                                        index = 0
                                        scrollState.animateScrollToItem(index)
                                    }
                                }) {
                                    Text(text = "U")
                                }
                                OutlinedButton(onClick = {
                                    increaseIndex()
                                    coroutineScope.launch {
                                        scrollState.animateScrollToItem(index)
                                    }
                                }) {
                                    Text(text = "D")
                                }
                            }
                        Divider(modifier = Modifier.size(16.dp))
                        LazyColumn(
                            state = scrollState
                        ) {
                            items(forecast) { hourlyForecast ->
                                Row() {
                                    Text(text = getTimeFromMilliseconds(hourlyForecast.dt.toLong()),
                                        color = Color.White)
                                    Divider(modifier = Modifier.size(5.dp))
                                    Text(text = stringResource(R.string.weather_temperature,
                                        hourlyForecast.temp ?: 0.0), color = Color.White)
                                    Divider(modifier = Modifier.size(5.dp))
                                    Text(text = hourlyForecast.description ?: "",
                                        color = Color.White)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
    val binding: WeatherItemBinding =
        WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return LocationViewHolder(binding)
}

override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
    val location = getItem(position)
    holder.bind(deleteLocationLambda, updateLocationWeather, location)
}

companion object {
    private val DiffCallback = object : DiffUtil.ItemCallback<CurrentAndForecastWeather>() {
        override fun areItemsTheSame(
            oldItem: CurrentAndForecastWeather,
            newItem: CurrentAndForecastWeather,
        ): Boolean {
            return oldItem.currentWeather.name == newItem.currentWeather.name && oldItem.currentWeather.country == newItem.currentWeather.country
        }

        override fun areContentsTheSame(
            oldItem: CurrentAndForecastWeather,
            newItem: CurrentAndForecastWeather,
        ): Boolean {
            return oldItem == newItem
        }
    }
}

}