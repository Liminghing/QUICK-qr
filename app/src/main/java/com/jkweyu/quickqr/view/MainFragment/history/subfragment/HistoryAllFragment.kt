package com.jkweyu.quickqr.view.MainFragment.history.subfragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryAllLayoutBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel


class HistoryAllFragment(): BaseFragment<FragmentHistoryAllLayoutBinding>(R.layout.fragment_history_all_layout) {
    override fun initView() {
        binding.apply {
            val mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
            Log.d("dfgjkl","HistoryQrCardFragment ${mainViewModel.qrCodeList.value} ")
            binding.allFragViewModel = mainViewModel
        }
    }
}