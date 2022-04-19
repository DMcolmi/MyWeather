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
import com.teddyDev.myweather.aWeatherDataProvider.WeatherDataProvider
import com.teddyDev.myweather.aWeatherDataProvider.WeatherDataProviderOpenW
import com.teddyDev.myweather.databinding.WeatherMeteoListBinding
import com.teddyDev.myweather.listAdapter.CurrentWeatherLocationListAdapter
import com.teddyDev.myweather.service.fromCurrentWeatherEntityToLocationEntity
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModel
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModelFactory
import com.teddyDev.myweather.viewModel.LocationViewModel
import com.teddyDev.myweather.viewModel.LocationViewModelFactory

class WeatherListFragment : Fragment() {
    private lateinit var binding: WeatherMeteoListBinding

    private val viewModel: LocationViewModel by activityViewModels {
        LocationViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getLocationDao(),
            WeatherDataProviderOpenW()
        )
    }

    private val currentWeatherViewModel: CurrentWeatherViewModel by activityViewModels{
        CurrentWeatherViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getCurrentWeatherDao(),
            activity?.application as WeatherApplication
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

            composeTestButton.setOnClickListener {
                findNavController().navigate(R.id.action_meteoListFragment_to_composeTestFragment)
            }

            val adapter = CurrentWeatherLocationListAdapter ({
                currentWeatherViewModel.deleteCurrentWeatherEntity(it)
                viewModel.deleteLocation(fromCurrentWeatherEntityToLocationEntity(it))
            }, {currentWeatherViewModel.updateCurrentWeatherDataForThisLocation(it)})

            currentWeatherViewModel.weatherData.observe(viewLifecycleOwner){ weatherData ->
                weatherData?.let { adapter.submitList(weatherData) }
            }
            weatherRecycler.adapter = adapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentWeatherViewModel.beginUniqueWorkToUpdateCurrentWeather()
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