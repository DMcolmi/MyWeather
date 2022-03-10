package com.teddyDev.myweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teddyDev.myweather.R
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.api.LocationData
import com.teddyDev.myweather.databinding.WeatherMeteoListBinding
import com.teddyDev.myweather.listAdapter.CurrentWeatherLocationListAdapter
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModel
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModelFactory
import com.teddyDev.myweather.viewModel.LocationViewModel
import com.teddyDev.myweather.viewModel.LocationViewModelFactory

class WeatherListFragment : Fragment() {
    private lateinit var binding: WeatherMeteoListBinding

    private val viewModel: LocationViewModel by activityViewModels {
        LocationViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getLocationDao()
        )
    }

    private val currentWeatherViewModel: CurrentWeatherViewModel by activityViewModels{
        CurrentWeatherViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getCurrentWeatherDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  WeatherMeteoListBinding.inflate(layoutInflater, container,false)

        binding.lifecycleOwner = this
        binding.apply {
            addNewLocationButton.setOnClickListener {
                findNavController().navigate(R.id.action_meteoListFragment_to_addLocationFragment)
                viewModel.clearFieldsToSearchNewLocation()
            }

            val adapter = CurrentWeatherLocationListAdapter ({
                currentWeatherViewModel.deleteCurrentWeatherData(it)
            }, {currentWeatherViewModel.updateCurrentWeatherDataForThisLocation(LocationData(lat =it.lat.toString(), lon = it.lon.toString()))})

            currentWeatherViewModel.weatherData.observe(viewLifecycleOwner){ weatherData ->
                weatherData?.let { adapter.submitList(weatherData) }
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