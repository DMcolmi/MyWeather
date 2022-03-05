package com.teddyDev.myweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.teddyDev.myweather.R
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.databinding.WeatherMeteoListBinding
import com.teddyDev.myweather.listAdapter.LocationListAdapter
import com.teddyDev.myweather.viewModel.LocationViewModel
import com.teddyDev.myweather.viewModel.LocationViewModelFactory

class WeatherListFragment : Fragment() {
    private lateinit var binding: WeatherMeteoListBinding

    private val viewModel: LocationViewModel by activityViewModels {
        LocationViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getLocationDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  WeatherMeteoListBinding.inflate(layoutInflater, container,false)

        binding.setLifecycleOwner(this)
        binding.apply {
            addNewLocationButton.setOnClickListener {
                findNavController().navigate(R.id.action_meteoListFragment_to_addLocationFragment)
                viewModel.newLocation = ""
                viewModel.retrievedLocationFromApi = MutableLiveData<List<LocationData>>()
                viewModel.isApiCalFinishedWithResult.value = false
            }

            val adapter = LocationListAdapter {
                viewModel.deleteLocation(it)
            }

            viewModel.locationList.observe(viewLifecycleOwner){ locations ->
                locations.let { adapter.submitList(it) }
            }
            weatherRecycler.adapter = adapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            WeatherListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}