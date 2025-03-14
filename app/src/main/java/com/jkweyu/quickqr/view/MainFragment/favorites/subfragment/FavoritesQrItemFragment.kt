package com.jkweyu.quickqr.view.MainFragment.favorites.subfragment

import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentFavoritesQrItemLayoutBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel

class FavoritesQrItemFragment: BaseFragment<FragmentFavoritesQrItemLayoutBinding>(
    R.layout.fragment_favorites_qr_item_layout) {
    override fun initView() {
        binding.apply {
            val mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
            binding.favViewModel = mainViewModel
        }
    }
}