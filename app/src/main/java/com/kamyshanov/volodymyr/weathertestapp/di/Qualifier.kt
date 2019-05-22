package com.kamyshanov.volodymyr.weathertestapp.di

import org.koin.core.qualifier.StringQualifier

object Qualifier {
  object OkHttpClient {
    val WEATHER_API_KEY = StringQualifier("WEATHER_API_KEY")
  }
}