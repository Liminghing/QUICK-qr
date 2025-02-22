package com.jkweyu.quickqr.view.MainFragment.favorites.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.ItemFavoritesLayoutBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel


class FavoritesItemViewHolder(
    private val binding: ItemFavoritesLayoutBinding,
    private val viewModel: MainViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: QRCodeItem) {
//
//        binding.favoritesViewModel = viewModel
//        binding.item = item
//        binding.root.setOnClickListener {
//            viewModel.onItemClicked(item)
//        }
    }
}