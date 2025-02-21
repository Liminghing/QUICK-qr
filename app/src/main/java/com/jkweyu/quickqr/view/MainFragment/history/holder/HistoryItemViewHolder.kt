package com.jkweyu.quickqr.view.MainFragment.history.holder

import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.ItemHistoryLayoutBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel

class HistoryItemViewHolder(
    private val binding: ItemHistoryLayoutBinding,
    private val viewModel: MainViewModel
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: QRCodeItem) {

        binding.item = item
        binding.root.setOnClickListener {
            viewModel.setFocusItem(item)
            viewModel.changeFragment(fragmentConstants.FRAME)
        }
    }
}

//class HistoryItemViewHolder(
//    private val binding: ItemHistoryLayoutBinding,
//    private val viewModel: HistoryRVItemViewModel
//) : RecyclerView.ViewHolder(binding.root) {
//    fun bind(item: HistoryRVItem) {
//
//        binding.historyViewModel = viewModel
//        binding.item = item
//        binding.root.setOnClickListener {
//            viewModel.onItemClicked(item)
//        }
//    }
//}