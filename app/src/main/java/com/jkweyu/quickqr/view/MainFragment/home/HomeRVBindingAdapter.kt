package com.jkweyu.quickqr.view.MainFragment.home

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


object HomeRVBindingAdapter {
    @BindingAdapter("viewModel","mainViewModel")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView,
                viewModel: HomeRVItemViewModel,
                mViewModel: MainViewModel
    ){
        if(recyclerView.adapter == null){

            val homeAdapter = NewHomeMultiRVAdapter(mViewModel,viewModel)

//            val gridLayoutManager = GridLayoutManager(recyclerView.context, 6)
//            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    when(homeAdapter.getItemViewType(position)){
//                        VIEW_TYPE_MAIN -> { return 6}
//                        VIEW_TYPE_CREATE_QR -> { return 3}
//                        VIEW_TYPE_SCAN_QR -> { return 3}
//                        VIEW_TYPE_MENU -> { return 2}
//                        VIEW_TYPE_ADD_MENU -> { return 2}
//                        else -> { return 6}
//                    }
//                }
//            }
            val gridLayoutManager = GridLayoutManager(recyclerView.context, 3)
            recyclerView.layoutManager = gridLayoutManager

            val callback: ItemTouchHelper.Callback = ItemMoveCallback(homeAdapter,viewModel)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)

            recyclerView.adapter = homeAdapter
        }
    }
}