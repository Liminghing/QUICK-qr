package com.jkweyu.quickqr.view.history.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryAllLayoutBinding
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel


class HistoryAllFragment(var historyViewModel : HistoryRVItemViewModel): BaseFragment<FragmentHistoryAllLayoutBinding>(R.layout.fragment_history_all_layout) {
    override fun initView() {
        binding.apply {
            binding.hisViewModel = historyViewModel
        }
    }
}