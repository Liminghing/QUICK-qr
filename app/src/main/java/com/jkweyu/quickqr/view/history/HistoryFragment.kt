package com.jkweyu.quickqr.view.history

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHistoryBinding
import com.jkweyu.quickqr.view.history.subfragment.HistoryAllFragment
import com.jkweyu.quickqr.view.history.subfragment.HistoryGeneralFragment
import com.jkweyu.quickqr.view.history.subfragment.HistoryQrCardFragment
import com.jkweyu.quickqr.view.history.subfragment.HistoryQrItemFragment
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel

class HistoryFragment: BaseFragment<FragmentHistoryBinding>(R.layout.fragment_history) {
    companion object {
        val VIEW_TYPE_HISTORY = 0
        val VIEW_TYPE_DATE = 1
    }
    private lateinit var historyViewModel: HistoryRVItemViewModel

    override fun initView() {
        historyViewModel = HistoryRVItemViewModel()
        binding.apply {
            hViewModel = historyViewModel
            lifecycleOwner = this@HistoryFragment

            loadFragment(HistoryAllFragment(historyViewModel))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {loadFragment(HistoryAllFragment(historyViewModel))}
                        1 -> {loadFragment(HistoryQrCardFragment(historyViewModel))}
                        2 -> {loadFragment(HistoryQrItemFragment(historyViewModel))}
                        else -> {loadFragment(HistoryGeneralFragment(historyViewModel))}
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })

            historyViewModel.selectedItem.observe(this@HistoryFragment, Observer {
                if (it != null){
                    Toast.makeText(this@HistoryFragment.context,"${it.title}",Toast.LENGTH_SHORT).show()
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
}