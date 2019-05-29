package com.kamyshanov.volodymyr.weathertestapp.data.repository

import android.location.Location
import com.kamyshanov.volodymyr.weathertestapp.data.database.WeatherDao
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.City
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherEntity
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.Mapper
import com.kamyshanov.volodymyr.weathertestapp.data.network.WeatherService
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WeatherResponse
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.repository.WeatherRepository
import io.reactivex.Single

class WeatherRepositoryImpl(
  private val weatherService: WeatherService,
  private val weatherDao: WeatherDao,
  private val weatherMapper: Mapper<WeatherResponse, WeatherEntity, Weather>
) : WeatherRepository {

  override fun getSavedCitiesList(): Single<List<City>> {
    return weatherDao.getCityList()
  }

  override fun getWeatherAroundLocation(location: Location): Single<List<Weather>> {
    return weatherService
      .getCurrentWeatherInArea(location.latitude, location.longitude)
      .doOnSuccess {
        weatherDao.saveWeatherList(it.list.map { response ->
          weatherMapper.mapResponseToDatabase(response)
        })
      }
      .map { result -> result.list.map { weatherMapper.mapResponseToDomain(it) } }
  }

  override fun getWeatherForCities(citiesIds: List<Long>): Single<List<Weather>> {
    return weatherService
      .getWeatherInCities(citiesIds.joinToString(separator = ","))
      .doOnSuccess {
        weatherDao.saveWeatherList(it.list.map { response ->
          weatherMapper.mapResponseToDatabase(response)
        })
      }
      .map { result -> result.list.map { weatherMapper.mapResponseToDomain(it) } }
      .onErrorResumeNext {
        weatherDao
          .getWeatherForCities(citiesIds)
          .map { result -> result.map { weatherMapper.mapDatabaseToDomain(it) } }
      }
  }


  override fun getWeatherByCityName(cityName: String): Single<Weather> {
    return weatherService
      .getCurrentWeatherByCityName(cityName)
      .doOnSuccess {
        weatherDao.saveWeather(weatherMapper.mapResponseToDatabase(it))
      }
      .map { weatherMapper.mapResponseToDomain(it) }
      .onErrorResumeNext {
        weatherDao
          .getWeatherByCityName(cityName)
          .map { weatherMapper.mapDatabaseToDomain(it) }
      }
  }
}