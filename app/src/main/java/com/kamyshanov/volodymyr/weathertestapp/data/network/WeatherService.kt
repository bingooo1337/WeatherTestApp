package com.kamyshanov.volodymyr.weathertestapp.data.network

import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WeatherListResponse
import com.kamyshanov.volodymyr.weathertestapp.data.network.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

  @GET("find")
  fun getCurrentWeatherInArea(
    @Query("lat") areaCenterLat: Double,
    @Query("lon") areaCenterLong: Double,
    @Query("cnt") cityCount: Int = 15
  ): Single<WeatherListResponse>

  @GET("group")
  fun getWeatherInCities(
    @Query("id") citiesIds: String
  ): Single<WeatherListResponse>

  @GET("weather")
  fun getCurrentWeatherByCityId(
    @Query("id") cityId: Long
  ): Single<WeatherResponse>

  @GET("weather")
  fun getCurrentWeatherByCityName(
    @Query("q") cityName: String
  ): Single<WeatherResponse>
}