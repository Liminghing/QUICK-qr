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
            Log.d("checkMainViewModel","HomeRVBindingAdapter ${mViewModel}")
            val homeAdapter = NewHomeMultiRVAdapter(mViewModel.vmList,mViewModel,viewModel)

            val gridLayoutManager = GridLayoutManager(recyclerView.context, 3)
            recyclerView.layoutManager = gridLayoutManager

            val callback: ItemTouchHelper.Callback = ItemMoveCallback(homeAdapter,viewModel)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)

            recyclerView.adapter = homeAdapter
        }
    }
}