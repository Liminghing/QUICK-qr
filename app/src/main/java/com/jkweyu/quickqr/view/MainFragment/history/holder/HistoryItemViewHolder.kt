package com.jkweyu.quickqr.view.MainFragment.history.holder

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.constants.itemHomeConstants
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

            when(viewModel.fragmentDepth.value){
                0 -> {
                    viewModel.setFocusItem(item,"history")
                    viewModel.changeFragment(fragmentConstants.FRAME)
                }
                1 -> {
                    val updateItem = item
                    updateItem.hPosition = itemHomeConstants.TRUE
                    Log.d("updateItemupdateItemupdateItem","HistoryItemViewHolder ${updateItem}")
                    viewModel.updateQRCodeItem(updateItem)
                    viewModel.setFocusItem(updateItem,null)
                    viewModel.changeFragment(fragmentConstants.MAIN)
                }

            }

//
//
//

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