package com.kamyshanov.volodymyr.weathertestapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class WeatherUnitsInterceptor : Interceptor {

  private companion object {
    const val UNITS = "units"
    const val METRIC = "metric"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val inputRequest = chain.request()
    val httpUrl = inputRequest.url()
        .newBuilder()
        .addQueryParameter(UNITS, METRIC)
        .build()
    val request = inputRequest.newBuilder().url(httpUrl).build()
    return chain.proceed(request)
  }
}