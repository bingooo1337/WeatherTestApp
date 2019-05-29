package com.kamyshanov.volodymyr.weathertestapp.presentation.addcity

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl.GetCityWeatherUseCase
import com.kamyshanov.volodymyr.weathertestapp.presentation.addcity.view.AddCityView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

@InjectViewState
class AddCityPresenter(
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
        viewState.showError("City not found or an error occurred")
      }
    }

    disposables.add(observer)
    getWeatherCityUseCase.execute(searchWord, observer)
  }
}