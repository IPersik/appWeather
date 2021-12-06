package com.example.weather.framework.receivers.ui.details

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.model.AppState
import com.example.weather.model.enitities.City
import com.example.weather.model.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: Repository): ViewModel(), LifecycleObserver {
    val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()

    fun loadData(city : City){
        liveDataToObserver.value = AppState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getWeatherFromServer(city.lat, city.lon)
            data.city = city
            repository.saveEntity(data)
            liveDataToObserver.postValue(AppState.Success(listOf(data)))
        }
    }
}