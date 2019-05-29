package com.kamyshanov.volodymyr.weathertestapp.presentation.addcity.view

import com.arellomobile.mvp.MvpView
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather

interface AddCityView : MvpView {
  fun showLoading()
  fun hideLoading()
  fun showError(errorMessage: String)
  fun hideError()
  fun closeViewWithResult(weatherResult: Weather)
}