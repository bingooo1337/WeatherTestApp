package com.kamyshanov.volodymyr.weathertestapp.data.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
class WeatherEntity(
  @PrimaryKey
  val cityId: Long,
  val cityName: String,
  val temp: Double,
  val pressure: Double,
  val humidity: Double,
  val maxTemp: Double,
  val minTemp: Double,
  @Embedded
  val wind: WindEntity,
  @Embedded
  val weather: WeatherDetailsEntity,
  val date: Long
)