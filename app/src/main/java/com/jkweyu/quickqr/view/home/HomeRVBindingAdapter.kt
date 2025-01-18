package com.jkweyu.quickqr.view.home

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_CREATE_QR
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_MAIN
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_SCAN_QR
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


object HomeRVBindingAdapter {
    @BindingAdapter("viewModel")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView,
                viewModel: HomeRVItemViewModel
    ){
        if(recyclerView.adapter == null){
            val homeAdapter = NewHomeMultiRVAdapter(viewModel.itemList.value!!,viewModel)

            val gridLayoutManager = GridLayoutManager(recyclerView.context, 6)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    when(homeAdapter.getItemViewType(position)){
                        VIEW_TYPE_MAIN -> { return 6}
                        VIEW_TYPE_CREATE_QR -> { return 3}
                        VIEW_TYPE_SCAN_QR -> { return 3}
                        VIEW_TYPE_MENU -> { return 2}
                        VIEW_TYPE_ADD_MENU -> { return 2}
                        else -> { return 6}
                    }
                }
            }
            recyclerView.layoutManager = gridLayoutManager

            val callback: ItemTouchHelper.Callback = ItemMoveCallback(homeAdapter,viewModel)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)

            recyclerView.adapter = homeAdapter
        }
    }
}