package com.example.weather.model.enitities

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize


@Parcelize
data class Weather(
    var city: City = City.getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val condition: String? = "Неизвестно"

) : Parcelable



