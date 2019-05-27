package com.kamyshanov.volodymyr.weathertestapp.utils.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class RecyclerViewEndScrollListener(
  private val triggerThreshold: Int,
  private val callbacks: PaginationCallbacks
) : RecyclerView.OnScrollListener() {

  override fun onScrolled(
    recyclerView: RecyclerView,
    dx: Int,
    dy: Int
  ) {
    recyclerView.layoutManager?.let { layoutManager ->
      val totalItemCount = layoutManager.itemCount

      val lastVisibleItemPosition = when (layoutManager) {
        is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
        is StaggeredGridLayoutManager -> {
          layoutManager.findLastVisibleItemPositions(null).max() ?: totalItemCount
        }
        else ->
          throw IllegalStateException(
              "LayoutManager needs to subclass LinearLayoutManager or StaggeredGridLayoutManager"
          )
      }

      if (!callbacks.isLoading && !callbacks.hasLoadedAllItems
          && lastVisibleItemPosition + triggerThreshold >= totalItemCount
      ) {
        callbacks.onLoadMore()
      }
    }
  }
}