package com.kamyshanov.volodymyr.weathertestapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherDetailsEntity
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherEntity
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WindEntity

@Database(
  entities = [WeatherEntity::class],
  version = 1,
  exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {

  companion object {
    const val DATABASE_NAME = "WEATHER_DATABASE"
  }

  abstract fun weatherDao(): WeatherDao
}