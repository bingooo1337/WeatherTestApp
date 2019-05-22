package com.kamyshanov.volodymyr.weathertestapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticateInterceptor(private val apiKey: String) : Interceptor {

  private companion object {
    const val APP_ID_KEY = "APPID"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val inputRequest = chain.request()
    val httpUrl = inputRequest.url()
      .newBuilder()
      .addQueryParameter(APP_ID_KEY, apiKey)
      .build()
    val request = inputRequest.newBuilder().url(httpUrl).build()
    return chain.proceed(request)
  }
}