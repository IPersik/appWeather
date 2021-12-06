package com.example.weather.di

import com.example.weather.model.repository.Repository
import com.example.weather.model.repository.RepositoryImpl
import com.example.weather.framework.receivers.ui.details.DetailsViewModel
import com.example.weather.framework.receivers.ui.history.HistoryViewModel
import com.example.weather.framework.receivers.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
   single<Repository> { RepositoryImpl() }
   viewModel { MainViewModel(get()) }
   viewModel { DetailsViewModel(get()) }
   viewModel { HistoryViewModel(get()) }
}


