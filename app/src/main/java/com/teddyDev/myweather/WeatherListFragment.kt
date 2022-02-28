package com.teddyDev.myweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.teddyDev.myweather.databinding.WeatherMeteoListBinding
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


        binding.apply {
            addNewLocationButton.setOnClickListener {
                Toast.makeText(activity, "Button pressed", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_meteoListFragment_to_addLocationFragment)
            }

            val adapter = LocationListAdapter()

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