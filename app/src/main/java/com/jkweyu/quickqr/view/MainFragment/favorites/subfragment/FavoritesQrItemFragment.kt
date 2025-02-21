package com.jkweyu.quickqr.view.MainFragment.favorites.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesQrItemLayoutBinding
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel

class FavoritesQrItemFragment(var favoritesViewModel : FavoritesRVItemViewModel): BaseFragment<FragmentFavoritesQrItemLayoutBinding>(
    R.layout.fragment_favorites_qr_item_layout) {
    override fun initView() {
        binding.apply {
            binding.favViewModel = favoritesViewModel
        }
    }
}