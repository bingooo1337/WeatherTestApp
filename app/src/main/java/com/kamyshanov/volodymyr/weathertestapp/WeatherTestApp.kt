package com.kamyshanov.volodymyr.weathertestapp

import android.app.Application
import com.kamyshanov.volodymyr.weathertestapp.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherTestApp : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@WeatherTestApp)
      modules(appComponent)
    }
  }
}