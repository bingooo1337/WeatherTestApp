package com.kamyshanov.volodymyr.weathertestapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WeatherDetailsEntity(
  val title: String,
  val description: String,
  val iconId: String,
  @PrimaryKey(autoGenerate = true)
  val detailsId: Long? = null
)