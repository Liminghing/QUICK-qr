package com.jkweyu.quickqr.viewmodel.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jkweyu.quickqr.Repository.FavoritesRepository
import com.jkweyu.quickqr.model.favorites.FavoritesRVItem


class FavoritesRVItemViewModel: ViewModel()  {
    private val _itemList = MutableLiveData<MutableList<FavoritesRVItem>>()
    val itemList: LiveData<MutableList<FavoritesRVItem>> get() = _itemList

    private val repository: FavoritesRepository

    private var items =  mutableListOf<FavoritesRVItem>()

    init {
        repository = FavoritesRepository()
        items = repository.getList()
        _itemList.value = items
    }

    fun loadList() : MutableList<FavoritesRVItem>{
        val myList = mutableListOf<FavoritesRVItem>()
        for (i in 0 until itemList.value!!.size) {
            myList.add(itemList.value!![i])
        }
        return myList
    }


    private val _selectedItem = MutableLiveData<FavoritesRVItem>(null) // 선택된 아이템 저장
    val selectedItem: LiveData<FavoritesRVItem> get() = _selectedItem

    fun onItemClicked(item: FavoritesRVItem) {
        _selectedItem.value = item // 클릭된 아이템 전달
    }
}