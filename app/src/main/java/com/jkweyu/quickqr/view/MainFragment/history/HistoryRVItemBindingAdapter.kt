package com.jkweyu.quickqr.view.MainFragment.history

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.constants.itemTypeConstants


object HistoryRVItemBindingAdapter {
    @BindingAdapter("setHistoryItemIcon")
    @JvmStatic
    fun setCardViewIconResource(imageView: ImageView, type: Int?) {
        val resource = when (type) {
            itemTypeConstants.QR_TYPE_TEXT -> R.drawable.ic_icon_text
            else -> R.drawable.ic_icon_link //itemTypeConstants.QR_TYPE_LINK
        }
        imageView.setImageResource(resource)
    }
}