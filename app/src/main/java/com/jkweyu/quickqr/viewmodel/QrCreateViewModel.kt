package com.jkweyu.quickqr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class QrCreateViewModel : ViewModel() {
    private val _createQRType = MutableLiveData<Int>()
    val createQRType: LiveData<Int> get() = _createQRType

    fun checkQRType(type:Int) {
        _createQRType.value = type
    }
}