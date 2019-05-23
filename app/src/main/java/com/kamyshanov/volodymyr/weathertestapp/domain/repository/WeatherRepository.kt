package com.kamyshanov.volodymyr.weathertestapp.domain.repository

import android.location.Location
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.City
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import io.reactivex.Single

interface WeatherRepository {
  fun getSavedCitiesList(): Single<List<City>>

  fun getWeatherAroundLocation(location: Location): Single<List<Weather>>

  fun getWeatherForCities(citiesIds: List<Long>): Single<List<Weather>>

  fun getWeatherByCityName(cityName: String): Single<Weather>
}