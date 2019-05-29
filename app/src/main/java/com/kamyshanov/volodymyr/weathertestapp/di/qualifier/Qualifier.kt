package com.kamyshanov.volodymyr.weathertestapp.di.qualifier

object Qualifier {
  object OkHttpClient {
    const val WEATHER_API_KEY = "WEATHER_API_KEY"
    const val AUTH_INTERCEPTOR = "AUTH_INTERCEPTOR"
    const val WEATHER_UNITS_INTERCEPTOR = "WEATHER_UNITS_INTERCEPTOR"
  }

  object Mapper {
    const val WEATHER_MAPPER = "WEATHER_MAPPER"
    const val WIND_MAPPER = "WIND_MAPPER"
    const val DETAILS_MAPPER = "DETAILS_MAPPER"
  }
}