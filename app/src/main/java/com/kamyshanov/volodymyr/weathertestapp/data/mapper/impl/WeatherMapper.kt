package com.kamyshanov.volodymyr.weathertestapp.data.mapper.impl

import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherDetailsEntity
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherEntity
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WindEntity
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.Mapper
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WeatherDetailsResponse
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WeatherResponse
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WindResponse
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Details
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Wind

class WeatherMapper(
  private val weatherDetailsMapper: Mapper<WeatherDetailsResponse, WeatherDetailsEntity, Details>,
  private val windMapper: Mapper<WindResponse, WindEntity, Wind>
) : Mapper<WeatherResponse, WeatherEntity, Weather> {

  override fun mapResponseToDomain(response: WeatherResponse): Weather {
    return Weather(
      response.cityId,
      response.cityName,
      response.main.temp,
      response.main.pressure,
      response.main.humidity,
      response.main.maxTemp,
      response.main.minTemp,
      windMapper.mapResponseToDomain(response.wind),
      weatherDetailsMapper.mapResponseToDomain(response.weather.first()),
      response.date
    )
  }

  override fun mapResponseToDatabase(response: WeatherResponse): WeatherEntity {
    return WeatherEntity(
      response.cityId,
      response.cityName,
      response.main.temp,
      response.main.pressure,
      response.main.humidity,
      response.main.maxTemp,
      response.main.minTemp,
      windMapper.mapResponseToDatabase(response.wind),
      weatherDetailsMapper.mapResponseToDatabase(response.weather.first()),
      response.date
    )
  }

  override fun mapDatabaseToDomain(database: WeatherEntity): Weather {
    return Weather(
      database.cityId,
      database.cityName,
      database.temp,
      database.pressure,
      database.humidity,
      database.maxTemp,
      database.minTemp,
      windMapper.mapDatabaseToDomain(database.wind),
      weatherDetailsMapper.mapDatabaseToDomain(database.weather),
      database.date
    )
  }
}