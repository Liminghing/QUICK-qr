package com.jkweyu.quickqr.view.MainFragment.favorites.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemFavoritesLayoutBinding
import com.jkweyu.quickqr.model.favorites.FavoritesRVItem
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel


class FavoritesItemViewHolder(
    private val binding: ItemFavoritesLayoutBinding,
    private val viewModel: FavoritesRVItemViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: FavoritesRVItem) {

        binding.favoritesViewModel = viewModel
        binding.item = item
        binding.root.setOnClickListener {
            viewModel.onItemClicked(item)
        }
    }
}