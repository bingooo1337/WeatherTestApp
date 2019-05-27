package com.kamyshanov.volodymyr.weathertestapp.utils.pagination

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kamyshanov.volodymyr.weathertestapp.R

abstract class PagedRecyclerViewAdapter<T>(
  protected val _items: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<ViewHolder>() {

  companion object {
    protected const val ITEM_VIEW_TYPE_LOADING = Int.MAX_VALUE - 99
  }

  val items: List<T>
    get() = _items.toList()

  var hasLoadedAllItems: Boolean = true

  abstract fun delegateOnCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder

  abstract fun delegateOnBindViewHolder(
    holder: ViewHolder,
    position: Int
  )

  open fun delegateGetItemViewType(position: Int): Int {
    return super.getItemViewType(position)
  }

  final override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return when (viewType) {
      ITEM_VIEW_TYPE_LOADING -> {
        LoadingViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_loading, parent, false)
        )
      }
      else -> delegateOnCreateViewHolder(parent, viewType)
    }
  }

  final override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    if (getItemViewType(position) != ITEM_VIEW_TYPE_LOADING) {
      delegateOnBindViewHolder(holder, position)
    }
  }

  final override fun getItemCount(): Int {
    return _items.size.let { if (!hasLoadedAllItems) it + 1 else it }
  }

  override fun getItemViewType(position: Int): Int {
    return when {
      isLastItem(position) && !hasLoadedAllItems -> ITEM_VIEW_TYPE_LOADING
      else -> delegateGetItemViewType(position)
    }
  }

  fun addItems(
    newItems: List<T>,
    hasLoadedAllItems: Boolean
  ) {
    val startPosition = itemCount + 1
    this.hasLoadedAllItems = hasLoadedAllItems
    _items.addAll(newItems)
    notifyItemRangeInserted(startPosition, newItems.size)
  }

  fun reset() {
    val oldItemCount = itemCount
    _items.clear()
    hasLoadedAllItems = true
    notifyItemRangeRemoved(1, oldItemCount)
  }

  private fun isLastItem(position: Int): Boolean = position + 1 == itemCount

  class LoadingViewHolder(itemView: View) : ViewHolder(itemView)
}