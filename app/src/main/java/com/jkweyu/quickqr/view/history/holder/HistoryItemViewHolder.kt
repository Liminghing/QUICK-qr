package com.jkweyu.quickqr.view.history.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHistoryLayoutBinding
import com.jkweyu.quickqr.model.history.HistoryRVItem
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel

class HistoryItemViewHolder(
    private val binding: ItemHistoryLayoutBinding,
    private val viewModel: HistoryRVItemViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: HistoryRVItem) {

        binding.historyViewModel = viewModel
        binding.item = item
        binding.root.setOnClickListener {
            viewModel.onItemClicked(item)
        }
    }
}