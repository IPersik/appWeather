package com.example.weather.model.repository

import com.example.weather.model.WeatherLoader
import com.example.weather.model.enitities.City
import com.example.weather.model.enitities.Weather
import com.example.weather.model.rest.WeatherRepo

class RepositoryImpl : Repository {
    override fun getWeatherFromServer(lat: Double, lng: Double): Weather{
        val dto = WeatherRepo.api.getWeather(lat, lng).execute().body()
        return Weather(
            temperature = dto?.fact?.temp ?: 0,
            feelsLike = dto?.fact?.feelsLike ?: 0,
            condition = dto?.fact?.condition,
        )
    }
    override fun getWeatherFromLocalStorageRus() = City.getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = City.getWorldCities()
}
