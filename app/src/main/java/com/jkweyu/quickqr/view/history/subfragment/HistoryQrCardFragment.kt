package com.jkweyu.quickqr.view.history.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryQrCardLayoutBinding
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel


class HistoryQrCardFragment(var historyViewModel : HistoryRVItemViewModel): BaseFragment<FragmentHistoryQrCardLayoutBinding>(R.layout.fragment_history_qr_card_layout) {
    override fun initView() {
        binding.apply {
            binding.hisViewModel = historyViewModel
        }
    }
}