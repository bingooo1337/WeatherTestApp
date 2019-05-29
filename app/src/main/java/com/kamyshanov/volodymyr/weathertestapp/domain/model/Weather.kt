package com.kamyshanov.volodymyr.weathertestapp.domain.model

class Weather(
  val cityId: Long,
  val cityName: String,
  val temp: Double,
  val pressure: Double,
  val humidity: Double,
  val maxTemp: Double,
  val minTemp: Double,
  val wind: Wind,
  val weather: Details,
  val date: Long
)