package com.kamyshanov.volodymyr.weathertestapp.presentation.weatherlist.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather

@StateStrategyType(AddToEndSingleStrategy::class)
interface WeatherListView : MvpView {
  fun showLoading()
  fun hideLoading()
  fun showWeatherList(weatherList: List<Weather>, hasLoadedAllItems: Boolean)
  fun showError(errorMessage: String)
  fun hideError()
  fun checkLocationPermission()
  fun addNewCityWeather(weather: Weather)
}