package com.teddyDev.myweather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.databinding.*
import com.teddyDev.myweather.databinding.WeatherMeteoListBinding

class LocationListAdapter: ListAdapter<LocationEntity, LocationListAdapter.LocationViewHolder>(DiffCallback){

    class LocationViewHolder(private var binding: WeatherItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(location: LocationEntity){
            binding.apply {
                weatherText.text = location.location
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding: WeatherItemBinding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<LocationEntity>(){
            override fun areItemsTheSame(
                oldItem: LocationEntity,
                newItem: LocationEntity
            ): Boolean {
                return oldItem.id == newItem.id
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