package com.jkweyu.quickqr.view.MainFragment.favorites

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesBinding
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesAllFragment
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesGeneralFragment
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

            loadFragment(FavoritesAllFragment(favoritesViewModel))
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab?.position) {
                        0 -> {loadFragment(FavoritesAllFragment(favoritesViewModel))}
                        1 -> {loadFragment(FavoritesQrCardFragment(favoritesViewModel))}
                        2 -> {loadFragment(FavoritesQrItemFragment(favoritesViewModel))}
                        else -> {loadFragment(FavoritesGeneralFragment(favoritesViewModel))}
                    }
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })

            favoritesViewModel.selectedItem.observe(this@FavoritesFragment, Observer {
                if (it != null){
                    Toast.makeText(this@FavoritesFragment.context,"${it.title}", Toast.LENGTH_SHORT).show()
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