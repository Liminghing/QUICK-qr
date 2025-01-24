package com.jkweyu.quickqr.view.history.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryGeneralLayoutBinding
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel


class HistoryGeneralFragment(var historyViewModel : HistoryRVItemViewModel): BaseFragment<FragmentHistoryGeneralLayoutBinding>(R.layout.fragment_history_general_layout) {
    override fun initView() {
        binding.apply {
            binding.hisViewModel = historyViewModel
        }
    }
}