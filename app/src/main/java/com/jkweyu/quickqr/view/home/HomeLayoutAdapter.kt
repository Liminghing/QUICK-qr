package com.jkweyu.quickqr.view.home

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.jkweyu.quickqr.R

object HomeLayoutAdapter {
    @JvmStatic
    @BindingAdapter("setCardViewIcon")
    fun setCardViewIconResource(imageView: ImageView, type: Int) {
        val resource = when (type) {
            1 -> R.drawable.ic_home_cardview_business_card
            2 -> R.drawable.ic_home_cardview_item_card
            3 -> R.drawable.ic_home_cardview_normal_card
            else -> 0 // 0으로 설정하면 이미지가 숨겨짐
        }
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("setCardViewQR")
    fun setCardViewQRResource(imageView: ImageView, type: Int) {
        val resource = when (type) {
            1 -> R.drawable.ic_home_cardview_qr
            2 -> R.drawable.ic_home_cardview_item_qr
            3 -> R.drawable.ic_home_cardview_normal_qr
            else -> 0 // 0으로 설정하면 이미지가 숨겨짐
        }
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("setCardViewResource")
    fun setCardViewResource(layout: ConstraintLayout, type: Int) {
        val resource = when (type) {
            1 -> R.drawable.shape_cardview_profile
            2 -> R.drawable.shape_cardview_item
            3 -> R.drawable.shape_cardview_normal
            else -> R.drawable.shape_cardview_add
        }
        layout.setBackgroundResource(resource)
    }

}