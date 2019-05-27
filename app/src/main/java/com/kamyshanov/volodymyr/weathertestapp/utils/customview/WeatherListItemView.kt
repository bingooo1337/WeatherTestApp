package com.kamyshanov.volodymyr.weathertestapp.utils.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import com.kamyshanov.volodymyr.weathertestapp.R
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import kotlinx.android.synthetic.main.view_item_weather_list.view.*

class WeatherListItemView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  init {
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    LayoutInflater
        .from(context)
        .inflate(R.layout.view_item_weather_list, this)
  }

  fun setWeatherData(weather: Weather) {
    cityName.text = weather.cityName
    weatherDescription.text = weather.weather.title
    temperature.text = resources.getString(R.string.temperature_placeholder, weather.temp)
    pressure.text = resources.getString(R.string.pressure_placeholder, weather.pressure)
    humidity.text = resources.getString(R.string.humidity_placeholder, weather.humidity)
  }
}