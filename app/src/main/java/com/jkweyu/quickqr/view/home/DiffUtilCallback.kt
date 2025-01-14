package com.jkweyu.quickqr.view.home

import androidx.recyclerview.widget.DiffUtil
import com.jkweyu.quickqr.viewmodel.HomeItem


class DiffUtilCallback(
    private val oldList: List<HomeItem>,
    private val newList: List<HomeItem>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size


    // 객체의 고유 값을 비교하는게 좋다.
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}