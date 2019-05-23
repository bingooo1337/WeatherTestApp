package com.kamyshanov.volodymyr.weathertestapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class WindEntity(
  val speed: Double,
  val directionDegrees: Double,
  @PrimaryKey(autoGenerate = true)
  val windId: Long? = null
)