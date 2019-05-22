package com.kamyshanov.volodymyr.weathertestapp.data.network.model

import com.google.gson.annotations.SerializedName

class WindResponse(
  val speed: Double,
  @SerializedName("deg")
  val directionDegrees: Double
)