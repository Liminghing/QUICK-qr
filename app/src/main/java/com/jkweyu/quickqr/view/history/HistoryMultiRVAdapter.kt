package com.jkweyu.quickqr.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHistoryDateLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHistoryLayoutBinding
import com.jkweyu.quickqr.model.history.HistoryRVItem
import com.jkweyu.quickqr.view.history.HistoryFragment.Companion.VIEW_TYPE_DATE
import com.jkweyu.quickqr.view.history.HistoryFragment.Companion.VIEW_TYPE_HISTORY
import com.jkweyu.quickqr.view.history.holder.HistoryDateViewHolder
import com.jkweyu.quickqr.view.history.holder.HistoryItemViewHolder
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel


class HistoryMultiRVAdapter(
    private var items : MutableList<HistoryRVItem>,
    private val viewModel: HistoryRVItemViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            VIEW_TYPE_HISTORY -> HistoryItemViewHolder(ItemHistoryLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
            VIEW_TYPE_DATE -> HistoryDateViewHolder(ItemHistoryDateLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).itemType) {
            VIEW_TYPE_HISTORY -> {
                (holder as HistoryItemViewHolder).bind(getItem(position))
            }
            VIEW_TYPE_DATE -> {
                (holder as HistoryDateViewHolder).bind(getItem(position))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // 아이템 타입 반환 메서드
    override fun getItemViewType(position: Int): Int {
        return items[position].itemType
//        return viewModel.loadList()[position].itemType
    }

    // 아이템 반환 메서드
    private fun getItem(position: Int): HistoryRVItem {
//        return viewModel.loadList()[position]
        return items[position]
    }


    // 아이템 개수 반환 메서
//    override fun getItemCount(): Int = viewModel.loadList().size
    override fun getItemCount(): Int = items.size

    // 아이템 고유 ID 반환 메서드
    override fun getItemId(position: Int): Long {
//        return if (position in viewModel.loadList().indices) {
//            viewModel.loadList()[position].hashCode().toLong()
//        } else {
//            -1L // 아이디를 찾지 못했을 때 반환되는 기본값
//        }
        return if (position in items.indices) {
            items[position].hashCode().toLong()
        } else {
            -1L // 아이디를 찾지 못했을 때 반환되는 기본값
        }

    }


}


