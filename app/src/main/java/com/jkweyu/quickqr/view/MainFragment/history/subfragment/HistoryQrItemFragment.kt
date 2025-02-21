package com.jkweyu.quickqr.view.MainFragment.history.subfragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryQrItemLayoutBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel


class HistoryQrItemFragment(): BaseFragment<FragmentHistoryQrItemLayoutBinding>(R.layout.fragment_history_qr_item_layout) {
    override fun initView() {

        binding.apply {
            val mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
            Log.d("dfgjkl","HistoryQrCardFragment ${mainViewModel.qrCodeList.value} ")
            binding.itemFragViewModel = mainViewModel
        }
    }
}