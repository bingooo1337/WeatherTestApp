package com.kamyshanov.volodymyr.weathertestapp.presentation.addcity

import android.content.res.Resources
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kamyshanov.volodymyr.weathertestapp.R
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetCityWeatherUseCase
import com.kamyshanov.volodymyr.weathertestapp.presentation.addcity.view.AddCityView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

@InjectViewState
class AddCityPresenter(
  private val resources: Resources,
  private val getWeatherCityUseCase: GetCityWeatherUseCase
) : MvpPresenter<AddCityView>() {

  private val disposables = CompositeDisposable()

  override fun onDestroy() {
    disposables.dispose()
    super.onDestroy()
  }

  fun onSearchClicked(searchWord: String) {
    viewState.hideError()
    viewState.showLoading()

    val observer = object : DisposableSingleObserver<Weather>() {
      override fun onSuccess(t: Weather) {
        viewState.closeViewWithResult(t)
      }

      override fun onError(e: Throwable) {
        viewState.hideLoading()
        viewState.showError(resources.getString(R.string.add_city_error))
      }
    }

    disposables.add(observer)
    getWeatherCityUseCase.execute(searchWord, observer)
  }
}