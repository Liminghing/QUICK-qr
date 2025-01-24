package com.jkweyu.quickqr.view.history

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jkweyu.quickqr.R


object HistoryRVItemBindingAdapter {
    @BindingAdapter("setHistoryItemIcon")
    @JvmStatic
    fun setCardViewIconResource(imageView: ImageView, type: Int?) {
        val resource = when (type) {
            1 -> R.drawable.ic_history_rv_item_business
            2 -> R.drawable.ic_history_rv_item_item
            else -> R.drawable.ic_history_rv_item_normal
        }
        imageView.setImageResource(resource)
    }
}