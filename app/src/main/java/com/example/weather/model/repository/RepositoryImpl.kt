package com.example.weather.model.repository

import com.example.weather.model.enitities.Weather
import com.example.weather.model.enitities.getRussianCities
import com.example.weather.model.enitities.getWorldCities

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()

    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()
}
