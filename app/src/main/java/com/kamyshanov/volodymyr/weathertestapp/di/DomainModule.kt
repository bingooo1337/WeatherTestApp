package com.kamyshanov.volodymyr.weathertestapp.di

import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetCityWeatherUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetSavedCitiesUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetUserLocationUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetWeatherAroundUserUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetWeatherInCitiesUseCase
import org.koin.dsl.module

val domainModule = module {
  factory { GetCityWeatherUseCase(get()) }
  factory { GetSavedCitiesUseCase(get()) }
  factory { GetUserLocationUseCase(get()) }
  factory { GetWeatherAroundUserUseCase(get()) }
  factory { GetWeatherInCitiesUseCase(get()) }
}