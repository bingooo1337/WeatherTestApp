package com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl

import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.repository.WeatherRepository
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.BaseSingleUseCase
import io.reactivex.Single

private typealias CityName = String

class GetCityWeatherUseCase(
  private val repository: WeatherRepository
) : BaseSingleUseCase<CityName, Weather>() {

  override fun buildUseCase(parameter: CityName): Single<Weather> {
    return repository.getWeatherByCityName(parameter)
  }
}