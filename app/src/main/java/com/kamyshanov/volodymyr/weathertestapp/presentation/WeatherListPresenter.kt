package com.kamyshanov.volodymyr.weathertestapp.presentation

import android.location.Location
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.City
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetSavedCitiesUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetUserLocationUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetWeatherAroundUserUseCase
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetWeatherInCitiesUseCase
import com.kamyshanov.volodymyr.weathertestapp.presentation.view.WeatherListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

@InjectViewState
class WeatherListPresenter(
  private val getSavedCitiesUseCase: GetSavedCitiesUseCase,
  private val getUserLocationUseCase: GetUserLocationUseCase,
  private val getWeatherAroundUserUseCase: GetWeatherAroundUserUseCase,
  private val getWeatherInCitiesUseCase: GetWeatherInCitiesUseCase
) : MvpPresenter<WeatherListView>() {

  private val disposables = CompositeDisposable()
  private val cities = mutableListOf<City>()

  override fun onFirstViewAttach() {
    super.onFirstViewAttach()
    viewState.showLoading()
    loadSavedCities()
  }

  override fun onDestroy() {
    disposables.dispose()
    super.onDestroy()
  }

  fun onLocationPermissionCheckResult(isGranted: Boolean) {
    if (isGranted) {
      val observer = object : DisposableSingleObserver<Location>() {
        override fun onSuccess(t: Location) {
          loadWeatherForLocation(t)
        }

        override fun onError(e: Throwable) {
          viewState.hideLoading()
          viewState.showError("No location permission")
        }
      }

      getUserLocationUseCase.execute(Unit, observer)
      disposables.add(observer)
    } else {
      viewState.hideLoading()
      viewState.showError("No saved cities and no location permission")
    }
  }

  fun onGoogleApiIsNotAvailable() {
    viewState.hideLoading()
    viewState.showError("GoogleApi is not available")
  }

  private fun loadSavedCities() {
    val observer = object : DisposableSingleObserver<List<City>>() {
      override fun onSuccess(t: List<City>) {
        if (t.isEmpty()) viewState.checkLocationPermission()
        else {
          loadWeatherForCities(t.take(10))
          cities.addAll(t)
        }
      }

      override fun onError(e: Throwable) {
        viewState.checkLocationPermission()
      }
    }
    disposables.add(observer)
    getSavedCitiesUseCase.execute(Unit, observer)
  }

  private fun loadWeatherForCities(cities: List<City>) {
    val observer = object : DisposableSingleObserver<List<Weather>>() {
      override fun onSuccess(t: List<Weather>) {
        viewState.hideLoading()
        viewState.showWeatherList(t)
      }

      override fun onError(e: Throwable) {
        viewState.hideLoading()
        viewState.showError("Weather loading error")
      }
    }

    getWeatherInCitiesUseCase.execute(cities.map { it.cityId }, observer)
    disposables.add(observer)
  }

  private fun loadWeatherForLocation(location: Location) {
    val observer = object : DisposableSingleObserver<List<Weather>>() {
      override fun onSuccess(t: List<Weather>) {
        viewState.hideLoading()
        viewState.showWeatherList(t)
      }

      override fun onError(e: Throwable) {
        viewState.hideLoading()
        viewState.showError("Weather around user loading error")
      }
    }

    getWeatherAroundUserUseCase.execute(location, observer)
    disposables.add(observer)
  }
}