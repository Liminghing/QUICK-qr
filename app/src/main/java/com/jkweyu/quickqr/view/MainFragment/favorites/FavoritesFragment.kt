package com.jkweyu.quickqr.view.MainFragment.favorites

import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesBinding
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesAllFragment
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesQrCardFragment
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesQrItemFragment
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel


class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>(R.layout.fragment_favorites) {
    private lateinit var favoritesViewModel: FavoritesRVItemViewModel
    override fun initView() {
        binding.apply {
            favoritesViewModel = FavoritesRVItemViewModel()
            fViewModel = favoritesViewModel
            lifecycleOwner = this@FavoritesFragment


            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {loadFragment(FavoritesAllFragment())}
                        1 -> {loadFragment(FavoritesQrCardFragment())}
                        else -> {loadFragment(FavoritesQrItemFragment())}
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
            Log.d("onHiddenChanged","<<<<HistoryFragment>>>> 보여짐")
            loadFragment(FavoritesAllFragment())
        }else{
            Log.d("onHiddenChanged","<<<<HistoryFragment>>>> 가려짐")
        }
    }
}