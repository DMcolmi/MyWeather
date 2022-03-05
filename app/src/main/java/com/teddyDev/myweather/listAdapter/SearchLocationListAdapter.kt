package com.teddyDev.myweather.listAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.databinding.SearchLocationItemBinding

class SearchLocationListAdapter(private val addLocationLambda: (LocationData)->Unit): ListAdapter<LocationData,SearchLocationListAdapter.SearchLocationViewHolder>(DiffCallback) {

    class SearchLocationViewHolder(private val lambda: (LocationData)->Unit, private val binding: SearchLocationItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(locationData: LocationData){
            binding.country.text = locationData.country
            binding.locationName.text = locationData.name
            binding.addNewLocationButton.setOnClickListener {
                lambda(locationData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchLocationViewHolder {
        val binding: SearchLocationItemBinding = SearchLocationItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchLocationViewHolder(addLocationLambda, binding)
    }

    override fun onBindViewHolder(holder: SearchLocationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        private val DiffCallback = object: DiffUtil.ItemCallback<LocationData>(){
            override fun areItemsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }

            override fun areContentsTheSame(oldItem: LocationData, newItem: LocationData): Boolean {
                return oldItem.name == newItem.name && oldItem.country == newItem.country
            }

        }
    }
}