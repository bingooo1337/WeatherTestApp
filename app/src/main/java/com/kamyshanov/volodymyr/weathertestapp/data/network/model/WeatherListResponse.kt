package com.kamyshanov.volodymyr.weathertestapp.data.network.model

import com.google.gson.annotations.SerializedName

class WeatherListResponse(
  @SerializedName("cnt")
  val count: Int,
  val list: List<WeatherResponse>
)