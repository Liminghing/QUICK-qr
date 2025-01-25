package com.jkweyu.quickqr.view.favorites.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesGeneralLayoutBinding
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel

class FavoritesGeneralFragment(var favoritesViewModel : FavoritesRVItemViewModel): BaseFragment<FragmentFavoritesGeneralLayoutBinding>(
    R.layout.fragment_favorites_general_layout) {
    override fun initView() {
        binding.apply {
            binding.favViewModel = favoritesViewModel
        }
    }
}