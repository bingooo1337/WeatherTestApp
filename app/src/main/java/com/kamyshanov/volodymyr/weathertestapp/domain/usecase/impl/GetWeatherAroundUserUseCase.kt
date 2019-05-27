package com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl

import android.location.Location
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.repository.WeatherRepository
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.BaseSingleUseCase
import io.reactivex.Single

class GetWeatherAroundUserUseCase(
  private val repository: WeatherRepository
) : BaseSingleUseCase<Location, List<Weather>>() {

  override fun buildUseCase(parameter: Location): Single<List<Weather>> {
    return repository.getWeatherAroundLocation(parameter)
  }
}