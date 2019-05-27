package com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl

import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.repository.WeatherRepository
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.BaseSingleUseCase
import io.reactivex.Single

private typealias CitiesIds = List<Long>

class GetWeatherInCitiesUseCase(
  private val repository: WeatherRepository
) : BaseSingleUseCase<CitiesIds, List<Weather>>() {

  override fun buildUseCase(parameter: List<Long>): Single<List<Weather>> {
    return repository.getWeatherForCities(parameter)
  }
}

