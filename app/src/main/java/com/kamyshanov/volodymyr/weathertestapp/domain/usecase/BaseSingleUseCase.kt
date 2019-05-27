package com.kamyshanov.volodymyr.weathertestapp.domain.usecase

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseSingleUseCase<in P, R> {

  protected abstract fun buildUseCase(parameter: P): Single<R>

  fun execute(
    parameter: P,
    observer: SingleObserver<R>
  ) {
    return buildUseCase(parameter)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.io())
      .subscribe(observer)
  }

}
