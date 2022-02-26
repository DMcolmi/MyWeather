package com.teddyDev.myweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.teddyDev.myweather.databinding.WeatherMeteoListBinding

class WeatherListFragment : Fragment() {
    private lateinit var binding: WeatherMeteoListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  WeatherMeteoListBinding.inflate(layoutInflater, container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewLocationButton.setOnClickListener {
            Toast.makeText(activity, "Button pressed", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_meteoListFragment_to_addLocationFragment)
        }
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