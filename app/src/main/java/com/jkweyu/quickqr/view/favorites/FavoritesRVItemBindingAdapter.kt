package com.jkweyu.quickqr.view.favorites

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.jkweyu.quickqr.R


object FavoritesRVItemBindingAdapter {
    @JvmStatic
    @BindingAdapter("setFavoritesItemIcon")
    fun setCardViewIconResource(imageView: ImageView, type: Int?) {
        val resource = when (type) {
            1 -> R.drawable.ic_rv_item_business
            2 -> R.drawable.ic_rv_item_item
            else -> R.drawable.ic_rv_item_normal
        }
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("setFavoritesCardViewResource")
    fun setFavoritesCardViewResource(layout: ConstraintLayout, type: Int?) {
        val resource = when (type) {
            1 -> R.drawable.shape_favorites_cardview_qr_card
            2 -> R.drawable.shape_favorites_cardview_normal
            else -> R.drawable.shape_favorites_cardview_qr_item
        }
        layout.setBackgroundResource(resource)
    }

    @JvmStatic
    @BindingAdapter("setCardSkeleton")
    fun setCardSkeleton(imageView: ImageView, type: Int?) {
        val record = when (type) {
            1 -> R.drawable.shape_skeleton_qr_card
            2 -> R.drawable.shape_skeleton_qr_item
            else -> R.drawable.shape_skeleton_general
        }
        imageView.setImageResource(record)
    }
}


