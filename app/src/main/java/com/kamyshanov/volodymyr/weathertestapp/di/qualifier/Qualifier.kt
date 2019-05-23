package com.kamyshanov.volodymyr.weathertestapp.di.qualifier

object Qualifier {
  object OkHttpClient {
    const val WEATHER_API_KEY = "WEATHER_API_KEY"
    const val AUTH_INTERCEPTOR = "AUTH_INTERCEPTOR"
  }
}