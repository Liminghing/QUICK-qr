package com.jkweyu.quickqr.view.MainFragment.home

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.Util.HomeRVList
import com.jkweyu.quickqr.databinding.ItemHomeAddMenuLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeEmptyLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMainLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMenuLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeQrCreateLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeQrScanLayoutBinding
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_CREATE_QR
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_EMPTY
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MAIN
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_SCAN_QR
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeAddMenuViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeEmptyViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeMainViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeMenuViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeQRCreateViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeQRScanViewHolder
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


class NewHomeMultiRVAdapter(
    private var items: HomeRVList,
    private val viewModel: HomeRVItemViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemMoveCallback.ItemTouchHelperContract {
    var animatorMap : MutableMap<Long, ObjectAnimator?> = mutableMapOf()
    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            VIEW_TYPE_MAIN -> HomeMainViewHolder(ItemHomeMainLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            VIEW_TYPE_CREATE_QR -> HomeQRCreateViewHolder(ItemHomeQrCreateLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
            VIEW_TYPE_SCAN_QR -> HomeQRScanViewHolder(ItemHomeQrScanLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
            VIEW_TYPE_MENU -> HomeMenuViewHolder(ItemHomeMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel,animatorMap)
            VIEW_TYPE_ADD_MENU -> HomeAddMenuViewHolder(ItemHomeAddMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
            VIEW_TYPE_EMPTY -> HomeEmptyViewHolder(ItemHomeEmptyLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItem(position).itemType) {
            VIEW_TYPE_MAIN -> {
                (holder as HomeMainViewHolder).bind(getItem(position))
            }
            VIEW_TYPE_CREATE_QR -> {
                (holder as HomeQRCreateViewHolder).bind(getItem(position))
            }
            VIEW_TYPE_SCAN_QR -> {
                (holder as HomeQRScanViewHolder).bind(getItem(position))
            }
            VIEW_TYPE_MENU -> {
                (holder as HomeMenuViewHolder).bind(getItem(position))
            }
            VIEW_TYPE_ADD_MENU -> {
                (holder as HomeAddMenuViewHolder).bind(getItem(position))
            }
            VIEW_TYPE_EMPTY -> {
                (holder as HomeEmptyViewHolder).bind(getItem(position))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    // 아이템 타입 반환 메서드
    override fun getItemViewType(position: Int): Int {
        when(val item = items[position]){
            is HomeItem -> {
                return item.itemType
            }
            else  -> {
                return VIEW_TYPE_MENU
            }
        }
    }

    // 아이템 반환 메서드
    private fun getItem(position: Int): HomeItem {
        when(val item = items[position]){
            is HomeItem -> {
                return item
            }
            is MutableList<*> -> {
                return item[position] as HomeItem
            }
            else  -> {
                throw IllegalArgumentException("Invalid view value")
            }
        }
    }

    //드래그 인터페이스
    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        val fromItem = items[fromPosition] as HomeItem
        val fromItemPosition = viewModel.getIndex(fromItem)
        val toItem = items[toPosition] as HomeItem
        val toItemPosition = viewModel.getIndex(toItem)
        if(toItem.itemType == VIEW_TYPE_MENU){
            if (fromPosition < toPosition) {
                for (i in fromItemPosition until toItemPosition) {
                    items.swapList(i,i+1)
                }
            } else {
                for (i in fromItemPosition downTo toItemPosition + 1) {
                    items.swapList(i,i-1)
                }
            }
            notifyItemMoved(fromPosition, toPosition)
        }
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


