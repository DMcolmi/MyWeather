package com.teddyDev.myweather.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.R
import com.teddyDev.myweather.database.CurrentWeatherEntity
import com.teddyDev.myweather.databinding.*
import com.teddyDev.myweather.service.getHyphenIfDoubleNull

class CurrentWeatherLocationListAdapter(private val deleteLocationLambda: (CurrentWeatherEntity) -> Unit, private val updateLocationWeather: (CurrentWeatherEntity) -> Unit): ListAdapter<CurrentWeatherEntity, CurrentWeatherLocationListAdapter.LocationViewHolder>(
    DiffCallback
){

    class LocationViewHolder(private var binding: WeatherItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(onClickDeleteLocationLambda: (CurrentWeatherEntity) -> Unit, updateLocationWeather: (CurrentWeatherEntity) -> Unit, weatherLocation: CurrentWeatherEntity){
            binding.apply {
                locationName.text = weatherLocation.name + " - " + weatherLocation.country + " - " + weatherLocation.state
                locationCoordinates.text = "("+weatherLocation.lat.toString() + " - " + weatherLocation.lon.toString()+")"

                deleteButton.setOnClickListener {
                    onClickDeleteLocationLambda(weatherLocation)
                }
                refreshWeatherButton.setOnClickListener {
                    updateLocationWeather(weatherLocation)
                }
                weatherDescription.text = weatherLocation.description?.uppercase()
                weatherTemperature.text = root.context.getString(R.string.weather_temperature,weatherLocation.temp)
                weatherMaxMinTemperature.text = root.context.getString(R.string.weather_temp_min_max
                    ,getHyphenIfDoubleNull(weatherLocation.tempMin)
                    ,getHyphenIfDoubleNull(weatherLocation.tempMax)
                )
                weatherWindSpeed.text = root.context.getString(R.string.weather_wind_speed,weatherLocation.windSpeed)
                weatherPressure.text = root.context.getString(R.string.weather_pressure,weatherLocation.pressure)
                weatherHumidity.text = root.context.getString(R.string.weather_humidity,weatherLocation.humidity)
                weatherLastUpdated.text = root.context.getString(R.string.weather_last_updated,weatherLocation.timestamp)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: WeatherItemBinding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(deleteLocationLambda,updateLocationWeather,location)
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<CurrentWeatherEntity>(){
            override fun areItemsTheSame(
                oldItem: CurrentWeatherEntity,
                newItem: CurrentWeatherEntity
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }

            override fun areContentsTheSame(
                oldItem: CurrentWeatherEntity,
                newItem: CurrentWeatherEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}