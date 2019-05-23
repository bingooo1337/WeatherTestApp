package com.kamyshanov.volodymyr.weathertestapp.di

import androidx.room.Room
import com.kamyshanov.volodymyr.weathertestapp.data.database.WeatherDatabase
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.impl.WeatherDetailsMapper
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.impl.WeatherMapper
import com.kamyshanov.volodymyr.weathertestapp.data.mapper.impl.WindMapper
import com.kamyshanov.volodymyr.weathertestapp.di.qualifier.Qualifier
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
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

  single(named(Qualifier.Mapper.WIND_MAPPER)) { WindMapper() }
  single(named(Qualifier.Mapper.DETAILS_MAPPER)) { WeatherDetailsMapper() }
  single(named(Qualifier.Mapper.WEATHER_MAPPER)) {
    WeatherMapper(
      get(named(Qualifier.Mapper.DETAILS_MAPPER)),
      get(named(Qualifier.Mapper.WIND_MAPPER))
    )
  }
}