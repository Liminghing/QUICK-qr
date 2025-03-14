package com.jkweyu.quickqr.view.MainFragment.home

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.jkweyu.quickqr.R

object HomeLayoutBindingAdapter {
    @JvmStatic
    @BindingAdapter("setCardViewIcon")
    fun setCardViewIconResource(imageView: ImageView, type: Int?) {
        val resource = when (type) {
            1 -> R.drawable.ic_h_position_text
            else -> R.drawable.ic_h_position_link
//            else -> R.drawable.ic_home_cardview_normal_card
        }
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("setCardViewQR")
    fun setCardViewQRResource(imageView: ImageView, type: Int?) {
        val resource = when (type) {
            1 -> R.drawable.ic_home_cardview_qr
            else -> R.drawable.ic_home_cardview_qr
//            2 -> R.drawable.ic_home_cardview_item_qr
//            else -> R.drawable.ic_home_cardview_normal_qr
        }
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("setCardViewResource")
    fun setCardViewResource(layout: ConstraintLayout, type: Int?) {
        val resource = when (type) {
            1 -> R.drawable.shape_cardview_profile
            else -> R.drawable.shape_cardview_profile
//            2 -> R.drawable.shape_cardview_item
//            else -> R.drawable.shape_cardview_normal
        }
        layout.setBackgroundResource(resource)
    }

}