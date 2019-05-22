package com.kamyshanov.volodymyr.weathertestapp.data.network.model

import com.google.gson.annotations.SerializedName

class MainResponse(
  val temp: Double,
  val pressure: Double,
  val humidity: Double,
  @SerializedName("temp_max")
  val maxTemp: Double,
  @SerializedName("temp_min")
  val minTemp: Double
)