package com.jkweyu.quickqr.view.favorites.subfragment

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesQrCardLayoutBinding
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel

class FavoritesQrCardFragment(var favoritesViewModel : FavoritesRVItemViewModel): BaseFragment<FragmentFavoritesQrCardLayoutBinding>(
    R.layout.fragment_favorites_qr_card_layout) {
    override fun initView() {
        binding.apply {
            binding.favViewModel = favoritesViewModel
        }
        
    }
}