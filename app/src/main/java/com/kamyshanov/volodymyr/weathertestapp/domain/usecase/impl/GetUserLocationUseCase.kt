package com.kamyshanov.volodymyr.weathertestapp.domain.usecase.impl

import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.kamyshanov.volodymyr.weathertestapp.domain.usecase.BaseSingleUseCase
import io.reactivex.Single

class GetUserLocationUseCase(
  private val fusedLocationClient: FusedLocationProviderClient
) : BaseSingleUseCase<Unit, Location>() {

  @RequiresPermission(
      anyOf = [
        "android.permission.ACCESS_COARSE_LOCATION",
        "android.permission.ACCESS_FINE_LOCATION"]
  )
  override fun buildUseCase(parameter: Unit): Single<Location> {
    return Single.create { emitter ->
      fusedLocationClient.lastLocation
          .addOnSuccessListener {
            if (it == null) emitter.onError(NullPointerException())
            else emitter.onSuccess(it)
          }
          .addOnFailureListener { emitter.onError(it) }
    }
  }
}