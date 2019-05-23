package com.kamyshanov.volodymyr.weathertestapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.City
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.WeatherEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WeatherDao {
  @Insert(onConflict = REPLACE)
  fun saveWeather(weather: WeatherEntity): Completable

  @Insert(onConflict = REPLACE)
  fun saveWeatherList(weather: List<WeatherEntity>): Completable

  @Query("SELECT cityId, cityName FROM weather")
  fun getCityList(): Single<List<City>>

  @Query("SELECT * FROM weather WHERE cityName = :cityName LIMIT 1")
  fun getWeatherByCityName(cityName: String): Single<WeatherEntity>

  @Query("SELECT * FROM weather WHERE cityId in (:citiesIds)")
  fun getWeatherForCities(citiesIds: List<Long>): Single<List<WeatherEntity>>
}