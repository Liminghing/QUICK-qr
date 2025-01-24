package com.jkweyu.quickqr.view.history.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryQrItemLayoutBinding
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel


class HistoryQrItemFragment(var historyViewModel : HistoryRVItemViewModel): BaseFragment<FragmentHistoryQrItemLayoutBinding>(R.layout.fragment_history_qr_item_layout) {
    override fun initView() {
        binding.apply {
            binding.hisViewModel = historyViewModel
        }
    }
}