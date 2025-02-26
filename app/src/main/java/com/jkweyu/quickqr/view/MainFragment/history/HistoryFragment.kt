package com.jkweyu.quickqr.view.MainFragment.history

import android.content.Context
import android.util.Log
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryBinding
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryAllFragment
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryQrCardFragment
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryQrItemFragment
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel

class HistoryFragment: BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    private var titleBar = false
    companion object {
        val VIEW_TYPE_HISTORY = 0
        val VIEW_TYPE_DATE = 1
    }
    private lateinit var historyViewModel: HistoryRVItemViewModel

    override fun initView() {
//        historyViewModel = HistoryRVItemViewModel()
        binding.apply {
//            hViewModel = historyViewModel
//            lifecycleOwner = this@HistoryFragment
            constraintLayout2.isGone = titleBar
            Log.d("onHiddenChanged","<<<<HistoryFragment>>>> : ${titleBar}")

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {loadFragment(HistoryAllFragment())}
                        1 -> {loadFragment(HistoryQrCardFragment())}
                        else -> {loadFragment(HistoryQrItemFragment())}
//                        else -> {loadFragment(HistoryGeneralFragment(historyViewModel))}
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })

        }

    }
    fun loadFragment(fragment: Fragment): Boolean {
        childFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragment)
            .commit()
        return true
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            titleBar = false
            binding.constraintLayout2.isGone = titleBar
            Log.d("onHiddenChanged","<<<<HistoryFragment>>>> 보여짐")
            loadFragment(HistoryAllFragment())
        }else{
            Log.d("onHiddenChanged","<<<<HistoryFragment>>>> 가려짐")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        titleBar = true
        loadFragment(HistoryAllFragment())
        Log.d("onHiddenChanged","<<<<HistoryFragment>>>> 보여짐 onAttach ")
    }
}