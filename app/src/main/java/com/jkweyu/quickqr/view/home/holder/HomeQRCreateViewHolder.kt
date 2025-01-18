package com.jkweyu.quickqr.view.home.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHomeQrCreateLayoutBinding
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


class HomeQRCreateViewHolder(
    val binding: ItemHomeQrCreateLayoutBinding,
    private val viewModel: HomeRVItemViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeItem) {
        binding.QRCREATEButton.setOnClickListener {
            viewModel.onItemClicked(item)
        }
    }
}