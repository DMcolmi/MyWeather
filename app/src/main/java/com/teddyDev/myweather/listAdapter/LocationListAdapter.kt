package com.teddyDev.myweather.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.databinding.*

class LocationListAdapter(private val deleteLocationLambda: (LocationEntity) -> Unit): ListAdapter<LocationEntity, LocationListAdapter.LocationViewHolder>(
    DiffCallback
){

    class LocationViewHolder(private var binding: WeatherItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(onClickDeleteLocationLambda: (LocationEntity) -> Unit, location: LocationEntity){
            binding.apply {
                locationName.text = location.name
                locationCountry.text = location.country
                locationCoordinates.text = location.lat + " - " + location.lon
                deleteButton.setOnClickListener {
                    onClickDeleteLocationLambda(location)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: WeatherItemBinding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(deleteLocationLambda,location)
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<LocationEntity>(){
            override fun areItemsTheSame(
                oldItem: LocationEntity,
                newItem: LocationEntity
            ): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }

            override fun areContentsTheSame(
                oldItem: LocationEntity,
                newItem: LocationEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}