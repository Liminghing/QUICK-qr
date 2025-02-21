package com.jkweyu.quickqr.view.MainFragment.history.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.ItemHistoryDateLayoutBinding

class HistoryDateViewHolder(
    private val binding: ItemHistoryDateLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: QRCodeItem) {
        binding.item = item
    }
}