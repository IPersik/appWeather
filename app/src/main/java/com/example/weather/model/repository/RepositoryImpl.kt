package com.example.weather.model.repository

import com.example.weather.model.enitities.City
import com.example.weather.model.enitities.Weather

class RepositoryImpl : Repository {
    override fun getWeatherFromServer() = Weather()
    override fun getWeatherFromLocalStorageRus() = City.getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = City.getWorldCities()
}
