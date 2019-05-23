package com.kamyshanov.volodymyr.weathertestapp.di

import androidx.room.Room
import com.kamyshanov.volodymyr.weathertestapp.data.database.WeatherDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
  single {
    Room
      .databaseBuilder(
        androidContext(),
        WeatherDatabase::class.java, WeatherDatabase.DATABASE_NAME
      )
      .build()
  }

  single { get<WeatherDatabase>().weatherDao() }
}