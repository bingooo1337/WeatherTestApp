package com.kamyshanov.volodymyr.weathertestapp.data.mapper.impl

import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WindEntity
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.Mapper
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WindResponse
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Wind

class WindMapper : Mapper<WindResponse, WindEntity, Wind> {

  override fun mapResponseToDomain(response: WindResponse): Wind {
    return Wind(response.speed, response.directionDegrees)
  }

  override fun mapResponseToDatabase(response: WindResponse): WindEntity {
    return WindEntity(response.speed, response.directionDegrees)
  }

  override fun mapDatabaseToDomain(database: WindEntity): Wind {
    return Wind(database.speed, database.directionDegrees)
  }
}