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
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_CREATE_QR
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_EMPTY
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MAIN
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeAddMenuViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeEmptyViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeMainViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeMenuViewHolder
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel
import java.util.Collections


class HomeMultiRVAdapter(
    private val list: MutableList<HomeItem>,
    private val viewModel: HomeRVItemViewModel,
    private val myOwner: HomeFragment,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemMoveCallback.ItemTouchHelperContract {
    var animatorMap : MutableMap<Long, ObjectAnimator?> = mutableMapOf()
    private var items : HomeRVList = HomeRVList(
        HomeItem(itemID = 11L,itemType = VIEW_TYPE_MAIN),
        HomeItem(itemID = 22L,itemType = VIEW_TYPE_CREATE_QR),
        HomeItem(itemID = 33L,itemType = VIEW_TYPE_CREATE_QR),
        list,
        HomeItem(itemID = 44L,itemType = VIEW_TYPE_ADD_MENU),
        HomeItem(itemID = 55L,itemType = VIEW_TYPE_EMPTY)
    )

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            VIEW_TYPE_MAIN -> HomeMainViewHolder(ItemHomeMainLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
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
                //QR 생성하기 버튼
            }
            VIEW_TYPE_MENU -> {
                (holder as HomeMenuViewHolder).bind(getItem(position))
                holder.binding.itemCard.setOnClickListener {
                    listener.onItemClick(itemId = getItemId(position),itemType = getItem(position).itemType)
                }
                holder.binding.deleteButton.setOnClickListener {
//                    removeItem(itemId = getItem(position).itemID)
                }

            }
            VIEW_TYPE_ADD_MENU -> {
                (holder as HomeAddMenuViewHolder).bind(getItem(position))
                holder.binding.itemCard.setOnClickListener {
                    listener.onItemClick(itemId = getItemId(position),itemType = getItem(position).itemType)
                    //addItem()
                }
            }
            VIEW_TYPE_EMPTY -> {
                (holder as HomeEmptyViewHolder).bind(getItem(position))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }
    // 아이템 타입 반환 메서드
    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        when(item){
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
        val item = items[position]
        when(item){
            is HomeItem -> {
                return item
            }
            is MutableList<*> -> {
                return item[position] as HomeItem
            }
            else  -> {
                return throw IllegalArgumentException("Invalid view value")
            }
        }
    }

    //드래그 인터페이스
    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        val fromItem = items[fromPosition] as HomeItem
        val fromItemPosition = list.indexOf(fromItem)
        val toItem = items[toPosition] as HomeItem
        val toItemPosition = list.indexOf(toItem)
        if(toItem.itemType == VIEW_TYPE_MENU){
            if (fromPosition < toPosition) {
                for (i in fromItemPosition until toItemPosition) {
                    Collections.swap(list, i, i+1)
                }
            } else {
                for (i in fromItemPosition downTo toItemPosition + 1) {
                    Collections.swap(list, i, i - 1)
                }
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }
    // 아이템 개수 반환 메서드
    override fun getItemCount(): Int = items.size
    // 아이템 고유 ID 반환 메서드
    override fun getItemId(position: Int): Long {
        return if (position in items.indices) {
            items[position].hashCode().toLong()
        } else {
            -1L // 아이디를 찾지 못했을 때 반환되는 기본값
        }
    }


    // 아이템 클릭 인터페이스 설정
    interface ItemClickListener {
        fun onItemClick(itemType : Int,itemId: Long)
    }
}

