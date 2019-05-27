package com.kamyshanov.volodymyr.weathertestapp.utils.pagination

interface PaginationCallbacks {

  var isLoading: Boolean

  var hasLoadedAllItems: Boolean

  fun onLoadMore()
}