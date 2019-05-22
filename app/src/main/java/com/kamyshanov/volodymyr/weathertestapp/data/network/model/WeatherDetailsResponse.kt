package com.kamyshanov.volodymyr.weathertestapp.data.network.model

import com.google.gson.annotations.SerializedName

class WeatherDetailsResponse(
  @SerializedName("main")
  val title: String,
  val description: String,
  @SerializedName("icon")
  val iconId: String
)