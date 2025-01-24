package com.jkweyu.quickqr.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jkweyu.quickqr.Repository.HistoryRepository
import com.jkweyu.quickqr.model.history.HistoryRVItem

class HistoryRVItemViewModel: ViewModel()  {
    private val _itemList = MutableLiveData<MutableList<HistoryRVItem>>()
    val itemList: LiveData<MutableList<HistoryRVItem>> get() = _itemList

    private val repository: HistoryRepository

    private var items =  mutableListOf<HistoryRVItem>()

    init {
        repository = HistoryRepository()
        items = repository.getList()
        _itemList.value = items
    }

    fun loadList() : MutableList<HistoryRVItem>{
        val myList = mutableListOf<HistoryRVItem>()
        for (i in 0 until itemList.value!!.size) {
            myList.add(itemList.value!![i])
        }
        return myList
    }


    private val _selectedItem = MutableLiveData<HistoryRVItem>(null) // 선택된 아이템 저장
    val selectedItem: LiveData<HistoryRVItem> get() = _selectedItem

    fun onItemClicked(item: HistoryRVItem) {
        _selectedItem.value = item // 클릭된 아이템 전달
    }

}