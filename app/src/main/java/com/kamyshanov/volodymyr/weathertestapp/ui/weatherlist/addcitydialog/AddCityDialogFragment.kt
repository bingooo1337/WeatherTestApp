package com.kamyshanov.volodymyr.weathertestapp.ui.weatherlist.addcitydialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.kamyshanov.volodymyr.weathertestapp.R
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.presentation.addcity.AddCityPresenter
import com.kamyshanov.volodymyr.weathertestapp.presentation.addcity.view.AddCityView
import kotlinx.android.synthetic.main.dialog_add_city.addCityError
import kotlinx.android.synthetic.main.dialog_add_city.addCityLoadingHud
import kotlinx.android.synthetic.main.dialog_add_city.addCitySearchButton
import kotlinx.android.synthetic.main.dialog_add_city.addCitySearchView
import org.koin.android.ext.android.inject

class AddCityDialogFragment : MvpAppCompatDialogFragment(), AddCityView {

  companion object {
    const val TAG_CITY_SELECTED = "TAG_CITY_SELECTED"
  }

  @InjectPresenter
  lateinit var presenter: AddCityPresenter
  @Deprecated("don't use this providedForMoxyPresenter, instead use presenter")
  val providedForMoxyPresenter: AddCityPresenter by inject()

  @ProvidePresenter
  fun providePresenter() = providedForMoxyPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BigDialogStyle)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.dialog_add_city, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)

    addCitySearchButton.setOnClickListener {
      presenter.onSearchClicked(addCitySearchView.query.toString())
    }
  }

  override fun showLoading() {
    addCityLoadingHud.visibility = View.VISIBLE
    isCancelable = false
  }

  override fun hideLoading() {
    addCityLoadingHud.visibility = View.GONE
    isCancelable = true
  }

  override fun showError(errorMessage: String) {
    addCityError.text = errorMessage
    addCityError.visibility = View.VISIBLE
  }

  override fun hideError() {
    addCityError.visibility = View.GONE
  }

  override fun closeViewWithResult(weatherResult: Weather) {
    val intent = Intent().apply { putExtra(TAG_CITY_SELECTED, weatherResult.cityName) }
    targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
    dismiss()
  }
}
