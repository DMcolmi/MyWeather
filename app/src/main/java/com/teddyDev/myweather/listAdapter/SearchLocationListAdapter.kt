package com.teddyDev.myweather.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.database.entity.LocationEntity
import com.teddyDev.myweather.databinding.SearchLocationItemBinding

class SearchLocationListAdapter(private val addLocationLambda: (LocationEntity)->Unit): ListAdapter<LocationEntity,SearchLocationListAdapter.SearchLocationViewHolder>(DiffCallback) {

    class SearchLocationViewHolder(private val binding: SearchLocationItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lambdaToAddLocation: (LocationEntity)->Unit, locationData: LocationEntity){
            binding.apply {
                countryState.text = locationData.country + " - " + locationData.state
                locationName.text = locationData.name
                locationCoordinates.text = locationData.lat + " - " + locationData.lon
                addNewLocationButton.setOnClickListener {
                    lambdaToAddLocation(locationData)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchLocationViewHolder {
        val binding: SearchLocationItemBinding = SearchLocationItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchLocationViewHolder( binding)
    }

    override fun onBindViewHolder(holder: SearchLocationViewHolder, position: Int) {
        holder.bind(addLocationLambda,getItem(position))
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<LocationEntity>(){
            override fun areItemsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }

            override fun areContentsTheSame(oldItem: LocationEntity, newItem: LocationEntity): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }

        }
    }
}