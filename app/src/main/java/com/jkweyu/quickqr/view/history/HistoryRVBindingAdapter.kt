package com.jkweyu.quickqr.view.history

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.model.history.HistoryRVItem
import com.jkweyu.quickqr.view.history.HistoryFragment.Companion.VIEW_TYPE_DATE
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel


object HistoryRVBindingAdapter {
    @BindingAdapter("hisViewModel","rvType")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView,
                viewModel: HistoryRVItemViewModel,
                type: Int
    ){
        if(recyclerView.adapter == null){
            val list = viewModel.loadList()
            val myList = insertItemOnDateChange(setListType(type,list),type)
            val historyAdapter = HistoryMultiRVAdapter(myList,viewModel)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = historyAdapter
        }
    }
    fun insertItemOnDateChange(items: MutableList<HistoryRVItem>,type:Int) : MutableList<HistoryRVItem>{
        val myList = items.map { it.copy() }.toMutableList()
        for (i in 0 until items.size) {
            when(i){
                0 -> {
                    myList.add(0,
                        HistoryRVItem(itemID = 0L,itemType = VIEW_TYPE_DATE, content = type.toString(),date = items[0].date)
                    )
                }
                in 1..items.size-2 ->{
                    val previousItem = items[i - 1]
                    val currentItem = items[i]
                    if (previousItem.date?.toLocalDate() != currentItem.date?.toLocalDate()) {
                        myList.add(myList.indexOf(currentItem), HistoryRVItem(itemID = 0L,itemType = VIEW_TYPE_DATE, content = type.toString(),date = items[i].date))
                    }
                }
                else ->{
                }
            }
        }
        return myList
    }
    fun setListType(type: Int,items : MutableList<HistoryRVItem>):MutableList<HistoryRVItem>{
        var list = items
        var menuType0List : MutableList<HistoryRVItem> = items
        if(type != 0){
            menuType0List = list.filter { it.qrType == type }.toMutableList()
        }
        return menuType0List
    }
}
