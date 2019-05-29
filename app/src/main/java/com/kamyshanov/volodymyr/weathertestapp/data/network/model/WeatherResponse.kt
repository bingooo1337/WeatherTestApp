package com.kamyshanov.volodymyr.weathertestapp.data.network.model

import com.google.gson.annotations.SerializedName

class WeatherResponse(
  val main: MainResponse,
  val wind: WindResponse,
  val weather: List<WeatherDetailsResponse>,
  @SerializedName("dt")
  val date: Long,
  @SerializedName("id")
  val cityId: Long,
  @SerializedName("name")
  val cityName: String
)