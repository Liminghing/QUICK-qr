package com.jkweyu.quickqr.view.MainFragment.history.holder

import android.util.Log
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
                    /**
                     * 나중에 필수적으로 처리해야할것
                     * depth의 수치를 지정하는 방식이 아닌 더하는 방식으로 변경할 필요가 있음
                     */

                    // all -> 타이틀프레임의 히스토리에서 클릭한 경우
                    viewModel.setFocusItem(item, "history")
                    viewModel.changeFragment(fragmentConstants.FRAME)

                }
                2 -> {
                    Log.d("checkItem","fragmentDepth : 2")
                    viewModel.setFocusItem(item, null)
                    viewModel.addVmItem(item)
                    viewModel.changeFragment(fragmentConstants.MAIN)
                }

            }
        }
    }
}