package com.example.weather.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.weather.AppState
import com.example.weather.model.enitities.Weather
import com.example.weather.R
import com.example.weather.databinding.MainFragmentBinding
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
        private val viewModel: MainViewModel by viewModel()
        private var _binding: MainFragmentBinding? = null
        private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            _binding = MainFragmentBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewLifecycleOwner.lifecycle.addObserver(viewModel)

            val observer = Observer<AppState> { renderData(it) }
            viewModel.getLiveData().observe(viewLifecycleOwner, observer)
            viewModel.getWeather()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun renderData(appState: AppState) = with(binding) {
            val rands = (1..2).random()
            if (rands == 2){
                when (appState) {
                is AppState.Error -> {
                    progressBar.visibility = android.view.View.GONE
                    weatherGroup.visibility = android.view.View.INVISIBLE
                    com.google.android.material.snackbar.Snackbar
                        .make(mainView, "Error", com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE)
                        .setAction("Reload") { viewModel.getWeather() }
                        .show()
                    }
                }
            } else {
                when (appState) {
                    is AppState.Success -> {
                        val weatherData = appState.weatherData
                        progressBar.visibility = View.GONE
                        weatherGroup.visibility = View.VISIBLE
                        setData(weatherData)
                    }
                    is AppState.Loading -> {
                        weatherGroup.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        private fun setData(weatherData: Weather) = with(binding) {
            cityName.text = weatherData.city.city
            cityCoordinates.text = String.format(
                getString(R.string.city_coordinates),
                weatherData.city.lat.toString(),
                weatherData.city.lon.toString()
            )
            temperatureValue.text = weatherData.temperature.toString()
            feelsLikeValue.text = weatherData.feelsLike.toString()
        }

        companion object {
            fun newInstance() = MainFragment()
        }
}