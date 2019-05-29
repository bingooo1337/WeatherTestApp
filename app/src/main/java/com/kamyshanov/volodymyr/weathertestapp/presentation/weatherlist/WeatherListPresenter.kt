package com.kamyshanov.volodymyr.weathertestapp.presentation.weatherlist

import android.location.Location
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kamyshanov.volodymyr.weathertestapp.data.database.model.City
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.*
import com.kamyshanov.volodymyr.weathertestapp.presentation.weatherlist.view.WeatherListView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

@InjectViewState
class WeatherListPresenter(
  private val getSavedCitiesUseCase: GetSavedCitiesUseCase,
  private val getUserLocationUseCase: GetUserLocationUseCase,
  private val getWeatherAroundUserUseCase: GetWeatherAroundUserUseCase,
  private val getWeatherInCitiesUseCase: GetWeatherInCitiesUseCase,
  private val getCityWeatherUseCase: GetCityWeatherUseCase
) : MvpPresenter<WeatherListView>() {

  private companion object {
    const val PAGE_SIZE = 10
  }

  private val disposables = CompositeDisposable()
  private val savedCities = mutableListOf<City>()

  private var pageNumber = 0

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
      }.also { disposables.add(it) }
      getUserLocationUseCase.execute(Unit, observer)
    } else {
      viewState.hideLoading()
      viewState.showError("No saved cities and no location permission")
    }
  }

  fun onGoogleApiIsNotAvailable() {
    viewState.hideLoading()
    viewState.showError("GoogleApi is not available")
  }

  fun onNewCityAdded(cityName: String) {
    val observer = object : DisposableSingleObserver<Weather>() {
      override fun onSuccess(t: Weather) {
        viewState.addNewCityWeather(t)
      }

      override fun onError(e: Throwable) {}

    }.also { disposables.add(it) }

    getCityWeatherUseCase.execute(cityName, observer)
  }

  fun loadNewWeatherListPage() {
    loadWeatherForCities(savedCities.drop(pageNumber * PAGE_SIZE).take(PAGE_SIZE))
  }

  private fun loadSavedCities() {
    val observer = object : DisposableSingleObserver<List<City>>() {
      override fun onSuccess(t: List<City>) {
        if (t.isEmpty()) viewState.checkLocationPermission()
        else {
          savedCities.addAll(t)
          loadNewWeatherListPage()
        }
      }

      override fun onError(e: Throwable) {
        viewState.checkLocationPermission()
      }
    }.also { disposables.add(it) }
    getSavedCitiesUseCase.execute(Unit, observer)
  }

  private fun loadWeatherForCities(cities: List<City>) {
    val observer = object : DisposableSingleObserver<List<Weather>>() {
      override fun onSuccess(t: List<Weather>) {
        viewState.hideLoading()
        viewState.showWeatherList(
            t, savedCities.size <= pageNumber * PAGE_SIZE + t.size
        )
        pageNumber++
      }

      override fun onError(e: Throwable) {
        viewState.hideLoading()
        viewState.showError("Weather loading error")
      }
    }.also { disposables.add(it) }
    getWeatherInCitiesUseCase.execute(cities.map { it.cityId }, observer)
  }

  private fun loadWeatherForLocation(location: Location) {
    val observer = object : DisposableSingleObserver<List<Weather>>() {
      override fun onSuccess(t: List<Weather>) {
        viewState.hideLoading()
        viewState.showWeatherList(t, true)
      }

      override fun onError(e: Throwable) {
        viewState.hideLoading()
        viewState.showError("Weather around user loading error")
      }
    }.also { disposables.add(it) }
    getWeatherAroundUserUseCase.execute(location, observer)
  }
}