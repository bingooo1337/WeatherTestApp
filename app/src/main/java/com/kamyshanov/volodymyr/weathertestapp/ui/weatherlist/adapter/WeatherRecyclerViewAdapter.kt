package com.kamyshanov.volodymyr.weathertestapp.ui.weatherlist.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kamyshanov.volodymyr.weathertestapp.domain.model.Weather
import com.kamyshanov.volodymyr.weathertestapp.utils.customview.WeatherListItemView
import com.kamyshanov.volodymyr.weathertestapp.utils.pagination.PagedRecyclerViewAdapter

class WeatherRecyclerViewAdapter
  : PagedRecyclerViewAdapter<Weather>() {

  override fun delegateOnCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return WeatherViewHolder(WeatherListItemView(parent.context))
  }

  override fun delegateOnBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    (holder as? WeatherViewHolder)?.setData(items[position])
  }

  class WeatherViewHolder(
    private val weatherView: WeatherListItemView
  ) : RecyclerView.ViewHolder(weatherView) {

    fun setData(data: Weather) {
      weatherView.setWeatherData(data)
    }
  }
}