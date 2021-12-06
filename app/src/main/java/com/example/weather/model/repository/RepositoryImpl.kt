package com.example.weather.model.repository

import com.example.weather.model.WeatherLoader
import com.example.weather.model.database.Database
import com.example.weather.model.database.HistoryEntity
import com.example.weather.model.enitities.City
import com.example.weather.model.enitities.Weather
import com.example.weather.model.enitities.getRussianCities
import com.example.weather.model.enitities.getWorldCities
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
    override fun getWeatherFromLocalStorageRus() = getRussianCities()
    override fun getWeatherFromLocalStorageWorld() = getWorldCities()

    override fun saveEntity(weather: Weather) {
        Database.db.historyDao().insert(convertWeatherToEntity(weather))
    }

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(Database.db.historyDao().all())
    }

    private fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
        return entityList.map {
            Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.condition)
        }
    }


    private fun convertWeatherToEntity(weather: Weather): HistoryEntity {
        return HistoryEntity(
            0, weather.city.city,
            weather.temperature ?: 0,
            weather.condition ?: ""
        )
    }
}
