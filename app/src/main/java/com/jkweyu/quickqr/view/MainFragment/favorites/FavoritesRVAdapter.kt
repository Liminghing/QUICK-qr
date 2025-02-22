package com.jkweyu.quickqr.view.MainFragment.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.ItemHistoryLayoutBinding
import com.jkweyu.quickqr.view.MainFragment.history.holder.HistoryItemViewHolder
import com.jkweyu.quickqr.viewmodel.MainViewModel


class FavoritesRVAdapter(
    private var items : MutableList<QRCodeItem>,
    private val viewModel: MainViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return HistoryItemViewHolder(ItemHistoryLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HistoryItemViewHolder).bind(getItem(position))
    }

    // 아이템 타입 반환 메서드
    override fun getItemViewType(position: Int): Int {
        return items[position].itemType
    }

    // 아이템 반환 메서드
    private fun getItem(position: Int): QRCodeItem {
        return items[position]
    }

    // 아이템 개수 반환 메서
    override fun getItemCount(): Int = items.size

    // 아이템 고유 ID 반환 메서드
    override fun getItemId(position: Int): Long {
        return if (position in items.indices) {
            items[position].hashCode().toLong()
        } else {
            -1L // 아이디를 찾지 못했을 때 반환되는 기본값
        }

    }


}