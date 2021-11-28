package com.example.weather.model.repository

import com.example.weather.model.enitities.Weather

interface Repository {
    fun getWeatherFromServer(lat: Double, lng: Double): Weather
    fun getWeatherFromLocalStorageRus(): List<Weather>
    fun getWeatherFromLocalStorageWorld(): List<Weather>
    fun saveEntity(weather: Weather)
    fun getAllHistory(): List<Weather>

}