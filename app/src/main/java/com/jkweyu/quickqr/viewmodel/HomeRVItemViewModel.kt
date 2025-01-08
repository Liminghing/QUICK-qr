package com.jkweyu.quickqr.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HomeRVItemViewModel : ViewModel() {
    private val _itemVisibility = MutableLiveData<Boolean>(false)
    val itemVisibility: LiveData<Boolean> get() {
        return _itemVisibility
    }
    fun toggleItemVisibility(itme : HomeItem) : Boolean {
        Log.d("ItemMoveCallback","--toggleItemVisibility 호출 : ${itemVisibility.value} in HomeRVItemViewModel--")
        if(itme.title != null && itme.type != null){
            _itemVisibility.value = true
        }
        return true
    }
    fun toggleItemVisibilityOff() : Boolean {
        Log.d("ItemMoveCallback","==toggleItemVisibilityOff 호출 : ${itemVisibility.value} in HomeRVItemViewModel==")
        _itemVisibility.value = false
        return true
    }
}

data class HomeItem(var type: Int?, var title: String?)

