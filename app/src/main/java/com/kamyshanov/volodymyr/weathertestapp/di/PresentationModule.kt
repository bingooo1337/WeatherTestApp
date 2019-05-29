package com.kamyshanov.volodymyr.weathertestapp.di

import com.kamyshanov.volodymyr.weathertestapp.presentation.WeatherListPresenter
import com.kamyshanov.volodymyr.weathertestapp.presentation.addcity.AddCityPresenter
import org.koin.dsl.module

val presentationModule = module {
  factory { WeatherListPresenter(get(), get(), get(), get(), get()) }

  factory { AddCityPresenter(get()) }
}