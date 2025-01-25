package com.jkweyu.quickqr.view.favorites.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesAllLayoutBinding
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel

class FavoritesAllFragment(var favoritesViewModel : FavoritesRVItemViewModel): BaseFragment<FragmentFavoritesAllLayoutBinding>(
    R.layout.fragment_favorites_all_layout) {
    override fun initView() {
        binding.apply {
            binding.favViewModel = favoritesViewModel
        }
    }
}