package com.jkweyu.quickqr.view.favorites

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.model.favorites.FavoritesRVItem
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel


object FavoritesRVBindingAdapter {
    @BindingAdapter("favViewModel","rvType")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView,
                viewModel: FavoritesRVItemViewModel,
                type: Int
    ){
        if(recyclerView.adapter == null){
            val list = viewModel.loadList()
            val historyAdapter = FavoritesRVAdapter(setListType(type,list),viewModel)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = historyAdapter
        }
    }
    fun setListType(type: Int,items : MutableList<FavoritesRVItem>):MutableList<FavoritesRVItem>{
        var list = items
        var menuType0List : MutableList<FavoritesRVItem> = items
        if(type != 0){
            menuType0List = list.filter { it.qrType == type }.toMutableList()
        }
        return menuType0List
    }
}
