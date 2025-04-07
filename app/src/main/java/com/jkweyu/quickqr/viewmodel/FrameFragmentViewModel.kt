package com.jkweyu.quickqr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jkweyu.quickqr.constants.fragmentConstantsFrame


class FrameFragmentViewModel : ViewModel() {
    private val _activityFragment = MediatorLiveData<Int>(fragmentConstantsFrame.QR_CHOICE)
    val activityFragment: LiveData<Int> get() = _activityFragment

    fun changeFragment(fragment : Int){
        _activityFragment.value = fragment
    }


    // 생성 타입용 인덱스값
    // 이동용 depth
    private val _createType = MutableLiveData<Int>(0) // 선택된 아이템 저장
    val createType: LiveData<Int> get() = _createType

    fun setType(type : Int) {
        _createType.value = type
    }

}