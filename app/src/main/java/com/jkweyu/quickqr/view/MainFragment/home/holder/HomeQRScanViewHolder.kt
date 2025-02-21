package com.jkweyu.quickqr.view.MainFragment.home.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHomeQrScanLayoutBinding
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


class HomeQRScanViewHolder(
    private val binding: ItemHomeQrScanLayoutBinding,
    private val viewModel: HomeRVItemViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HomeItem) {
        binding.QRSCANButton.setOnClickListener {
            viewModel.onItemClicked(item)
        }
    }
}