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

            when(viewModel.fragmentDepth.value) {
                0 -> {
                    // 히스토리에서 클릭한 경우
                    viewModel.setFocusItem(item, "history")
                    viewModel.changeFragment(fragmentConstants.FRAME)
                }

                1 -> {
                    // all -> 타이틀프레임의 히스토리에서 클릭한 경우
                    viewModel.setFocusItem(item, null)
                    viewModel.addVmItem(item)
                    viewModel.changeFragment(fragmentConstants.MAIN)
                }

            }
        }
    }
}