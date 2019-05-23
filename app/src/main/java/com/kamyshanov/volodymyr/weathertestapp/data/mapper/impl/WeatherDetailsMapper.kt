package com.kamyshanov.volodymyr.weathertestapp.data.mapper.impl

import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherDetailsEntity
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.Mapper
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WeatherDetailsResponse
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Details

class WeatherDetailsMapper : Mapper<WeatherDetailsResponse, WeatherDetailsEntity, Details> {

  override fun mapResponseToDomain(response: WeatherDetailsResponse): Details {
    return Details(response.title, response.description, response.iconId)
  }

  override fun mapResponseToDatabase(response: WeatherDetailsResponse): WeatherDetailsEntity {
    return WeatherDetailsEntity(response.title, response.description, response.iconId)
  }

  override fun mapDatabaseToDomain(database: WeatherDetailsEntity): Details {
    return Details(database.title, database.description, database.iconId)
  }
}