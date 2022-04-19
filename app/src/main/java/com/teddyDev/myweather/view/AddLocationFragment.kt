package com.teddyDev.myweather.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teddyDev.myweather.R
import com.teddyDev.myweather.WeatherApplication
import com.teddyDev.myweather.aWeatherDataProvider.WeatherDataProviderOpenW
import com.teddyDev.myweather.database.entity.LocationEntity
import com.teddyDev.myweather.databinding.FragmentAddLocationBinding
import com.teddyDev.myweather.listAdapter.SearchLocationListAdapter
import com.teddyDev.myweather.service.fromLocationEntityToCurrentWeatherEntity
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModel
import com.teddyDev.myweather.viewModel.CurrentWeatherViewModelFactory
import com.teddyDev.myweather.viewModel.LocationViewModel
import com.teddyDev.myweather.viewModel.LocationViewModelFactory

class AddLocationFragment: Fragment() {

    private val viewModel: LocationViewModel by activityViewModels {
        LocationViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getLocationDao(),
            WeatherDataProviderOpenW()
        )
    }

    private val currentWeatherViewModel: CurrentWeatherViewModel by activityViewModels {
        CurrentWeatherViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getCurrentWeatherDao(),
            activity?.application as WeatherApplication
        )
    }

    private lateinit var binding: FragmentAddLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLocationBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SearchLocationListAdapter{
            addLocationWeatherDataAndNavigateBack(it)
        }
        viewModel.retrievedLocationFromApi.observe(this.viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.apply {

            addLocationFrg = this@AddLocationFragment
            viewM = viewModel
            addLocationRecyclerView.adapter = adapter
            searchNewLocationInputText.setOnKeyListener { view, keyCode, _ ->
                handleKeyEvent(view,keyCode)
            }
        }
    }

    fun searchNewLocation(newLocation: String){
        viewModel.searchLocation(newLocation)
        hideSoftKey(view)
    }

    private fun addLocationWeatherDataAndNavigateBack(location: LocationEntity){
        viewModel.saveLocation(location)
        currentWeatherViewModel.updateCurrentWeatherDataForThisLocation(fromLocationEntityToCurrentWeatherEntity(location))
        navigateToWeatherListFragment()
    }

    private fun navigateToWeatherListFragment(){
        findNavController().navigate(R.id.action_addLocationFragment_to_meteoListFragment)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean{
        if(keyCode == KeyEvent.KEYCODE_ENTER){
            hideSoftKey(view)
            return true
        }
        return false
    }

    private fun hideSoftKey(view :View?){
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken,0)
    }
}