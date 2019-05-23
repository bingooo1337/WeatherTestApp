package com.kamyshanov.volodymyr.weathertestapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kamyshanov.volodymyr.weathertestapp.BuildConfig
import com.kamyshanov.volodymyr.weathertestapp.R
import com.kamyshanov.volodymyr.weathertestapp.data.network.AuthenticateInterceptor
import com.kamyshanov.volodymyr.weathertestapp.data.network.WeatherService
import com.kamyshanov.volodymyr.weathertestapp.di.qualifier.Qualifier
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val REQUEST_TIMEOUT = 30L

val networkModule = module {
  single<OkHttpClient> {
    OkHttpClient.Builder()
      .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
      .addInterceptor(get(named(Qualifier.OkHttpClient.AUTH_INTERCEPTOR)))
      .build()
  }

  single { GsonBuilder().create() }

  single { retrofit(get(), get()) }

  single(named(Qualifier.OkHttpClient.AUTH_INTERCEPTOR)) {
    AuthenticateInterceptor(get(named(Qualifier.OkHttpClient.WEATHER_API_KEY)))
  }

  single(named(Qualifier.OkHttpClient.WEATHER_API_KEY)) {
    androidContext().getString(R.string.open_weather_map_key)
  }

  single { getWeatherService(get()) }
}

private fun retrofit(
  client: OkHttpClient,
  gson: Gson
): Retrofit {
  return Retrofit.Builder()
    .baseUrl(BuildConfig.SERVER_URL)
    .client(client)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()
}

private fun getWeatherService(retrofit: Retrofit): WeatherService {
  return retrofit.create(WeatherService::class.java)
}


