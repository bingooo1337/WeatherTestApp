package com.kamyshanov.volodymyr.weathertestapp.ui.weatherlist

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.gms.common.ConnectionResult.SUCCESS
import com.google.android.gms.common.GoogleApiAvailability
import com.kamyshanov.volodymyr.weathertestapp.R
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.presentation.WeatherListPresenter
import com.kamyshanov.volodymyr.weathertestapp.presentation.view.WeatherListView
import com.kamyshanov.volodymyr.weathertestapp.ui.weatherlist.adapter.WeatherRecyclerViewAdapter
import com.kamyshanov.volodymyr.weathertestapp.ui.weatherlist.addcitydialog.AddCityDialogFragment
import kotlinx.android.synthetic.main.fragment_weather_list.addCityButton
import kotlinx.android.synthetic.main.fragment_weather_list.errorText
import kotlinx.android.synthetic.main.fragment_weather_list.loadingHud
import kotlinx.android.synthetic.main.fragment_weather_list.weatherListRecyclerView
import org.koin.android.ext.android.inject

class WeatherListFragment : MvpAppCompatFragment(), WeatherListView {

  private companion object {
    const val LOCATION_PERMISSION_REQUEST_CODE = 1
    const val ADD_CITY_REQUEST_CODE = 11
  }

  @InjectPresenter
  lateinit var presenter: WeatherListPresenter
  @Deprecated("don't use this providedForMoxyPresenter, instead use presenter")
  val providedForMoxyPresenter: WeatherListPresenter by inject()

  private val adapter = WeatherRecyclerViewAdapter()

  @ProvidePresenter
  fun providePresenter() = providedForMoxyPresenter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_weather_list, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    addCityButton.setOnClickListener {
      AddCityDialogFragment()
          .apply { setTargetFragment(this@WeatherListFragment, ADD_CITY_REQUEST_CODE) }
          .show(fragmentManager, "AddCityDialogFragment")
    }
    weatherListRecyclerView.layoutManager = LinearLayoutManager(context)
    weatherListRecyclerView.adapter = adapter
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
      val locationIndex = permissions.indexOf(Manifest.permission.ACCESS_FINE_LOCATION)
      if (locationIndex >= 0) {
        presenter.onLocationPermissionCheckResult(
            grantResults[locationIndex] == PERMISSION_GRANTED
        )
      }
    }
  }

  override fun onActivityResult(
    requestCode: Int,
    resultCode: Int,
    data: Intent?
  ) {
    if (requestCode == ADD_CITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
      data?.getStringExtra(AddCityDialogFragment.TAG_CITY_SELECTED)
          ?.let { presenter.onNewCityAdded(it) }
    }
  }

  override fun showLoading() {
    loadingHud.visibility = View.VISIBLE
  }

  override fun hideLoading() {
    loadingHud.visibility = View.GONE
  }

  override fun showWeatherList(weatherList: List<Weather>) {
    adapter.addItems(weatherList, true)
  }

  override fun showError(errorMessage: String) {
    errorText.text = errorMessage
    errorText.visibility = View.VISIBLE
  }

  override fun hideError() {
    errorText.visibility = View.GONE
  }

  override fun checkLocationPermission() {
    val googlePlayServicesAvailable = GoogleApiAvailability.getInstance()
        .isGooglePlayServicesAvailable(context)

    if (googlePlayServicesAvailable == SUCCESS) {

      context?.let {
        val locationPermissionGranted =
          checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION)

        if (locationPermissionGranted == PERMISSION_GRANTED) {
          presenter.onLocationPermissionCheckResult(true)
        } else {
          requestPermissions(
              arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
              LOCATION_PERMISSION_REQUEST_CODE
          )
        }

      }
    } else {
      presenter.onGoogleApiIsNotAvailable()
    }
  }

  override fun addNewCityWeather(weather: Weather) {
    adapter.addItemAtPosition(weather, 0)
    weatherListRecyclerView.smoothScrollToPosition(0)
  }
}