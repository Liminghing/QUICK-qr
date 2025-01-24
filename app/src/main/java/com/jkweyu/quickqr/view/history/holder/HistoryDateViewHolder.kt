package com.jkweyu.quickqr.view.history.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHistoryDateLayoutBinding
import com.jkweyu.quickqr.model.history.HistoryRVItem

class HistoryDateViewHolder(
    private val binding: ItemHistoryDateLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HistoryRVItem) {
        binding.item = item
    }
}