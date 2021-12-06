package com.example.weather.experiments

import com.example.weather.model.enitities.City

interface FlyingVehicle {
    val city: City

    val greeting: String
    get() = "Hello from the air!"

    fun takeOff(): Unit
    fun land(): Unit
    fun getHeight(): Double

    fun warmUp() {
        city.toString()
    }
}