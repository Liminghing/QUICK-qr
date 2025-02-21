package com.jkweyu.quickqr.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.jkweyu.quickqr.constants.fragmentConstantsFrame


class FrameFragmentViewModel : ViewModel() {
    private val _activityFragment = MediatorLiveData<Int>(fragmentConstantsFrame.QR_CHOICE)
    val activityFragment: LiveData<Int> get() = _activityFragment

    fun changeFragment(fragment : Int){
        _activityFragment.value = fragment
    }
}