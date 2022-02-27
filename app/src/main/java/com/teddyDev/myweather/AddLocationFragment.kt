package com.teddyDev.myweather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.teddyDev.myweather.database.LocationEntity
import com.teddyDev.myweather.databinding.FragmentAddLocationBinding
import com.teddyDev.myweather.viewModel.LocationViewModel
import com.teddyDev.myweather.viewModel.LocationViewModelFactory

class AddLocationFragment: Fragment() {

    private val viewModel: LocationViewModel by activityViewModels {
        LocationViewModelFactory(
            (activity?.application as WeatherApplication).appDatabase.getLocationDao()
        )
    }

    private lateinit var binding: FragmentAddLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddLocationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addLocationFrg = this@AddLocationFragment
            viewM = viewModel
        }
    }

    fun addLocation(location: String){
        Log.i("AddLocationFragment","addLocation called")
        viewModel.saveLocation(location)
    }
}