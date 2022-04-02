package com.teddyDev.myweather.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.databinding.SearchLocationItemBinding

class WidgetLocationListAdapter(private val addLocationLambda: (LocationEntity)->Unit): ListAdapter<LocationEntity,WidgetLocationListAdapter.SearchLocationViewHolder>(DiffCallback) {

    class SearchLocationViewHolder(private val binding: SearchLocationItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lambdaToAddLocation: (LocationEntity)->Unit, locationEntity: LocationEntity){
            binding.apply {
                countryState.text = locationEntity.country + " - " + locationEntity.state
                locationName.text = locationEntity.name
                locationCoordinates.text = locationEntity.lat + " - " + locationEntity.lon
                addNewLocationButton.setOnClickListener {
                    lambdaToAddLocation(locationEntity)
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