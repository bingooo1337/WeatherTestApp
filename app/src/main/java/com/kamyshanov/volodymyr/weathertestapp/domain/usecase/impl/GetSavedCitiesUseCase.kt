package com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl

import com.kamyshanov.volodymyr.weathertestapp.data.database.model.City
import com.kamyshanov.volodymyr.weathertestapp.domain.repository.WeatherRepository
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.BaseSingleUseCase
import io.reactivex.Single

class GetSavedCitiesUseCase(
  private val repository: WeatherRepository
) : BaseSingleUseCase<Unit, List<City>>() {

  override fun buildUseCase(parameter: Unit): Single<List<City>> {
    return repository.getSavedCitiesList()
  }
}