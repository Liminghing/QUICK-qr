package com.jkweyu.quickqr.viewmodel.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class AllItemViewModel: ViewModel()  {
    private val _selectedItem = MutableLiveData<String>(null) // 선택된 아이템 저장
    val selectedItem: LiveData<String> get() = _selectedItem

    fun onItemClicked(type: String) {
        _selectedItem.value = type // 클릭된 아이템 전달
    }
}