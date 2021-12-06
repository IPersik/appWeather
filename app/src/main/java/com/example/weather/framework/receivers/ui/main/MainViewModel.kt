package com.example.weather.framework.receivers.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.weather.model.AppState
import com.example.weather.model.repository.Repository
import kotlinx.coroutines.*

class MainViewModel(private val repository: Repository)
    : ViewModel(), LifecycleObserver, CoroutineScope by MainScope() {
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun getLiveData() = liveDataToObserve

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(isRussian = true)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        launch {
            delay(1000)
            val localStorageJob = async(Dispatchers.IO) {
                if (isRussian) repository.getWeatherFromLocalStorageRus()
                else repository.getWeatherFromLocalStorageWorld()
            }
            liveDataToObserve.value = AppState.Success(localStorageJob.await())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart() {
        Log.i("LifecycleEvent", "onStart")
    }
}
/*open class MainViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(true)

    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(false)

    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveData.value = AppState.Loading
        Thread {
            sleep(1000)
            liveData.postValue(
                if(isRussian){
                    AppState.Success(repository.getWeatherFromLocalStorageRus())
                } else {
                    AppState.Success(repository.getWeatherFromLocalStorageWorld())
                }
            )

        }.start()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onViewStart() {
        Log.i("LifecycleEvent", "onStart")
    }
} */