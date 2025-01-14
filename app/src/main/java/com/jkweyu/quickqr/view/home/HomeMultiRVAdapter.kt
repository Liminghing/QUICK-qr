package com.jkweyu.quickqr.view.home

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHomeAddMenuLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeEmptyLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMainLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMenuLayoutBinding
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_EMPTY
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_MAIN
import com.jkweyu.quickqr.view.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.view.home.holder.HomeAddMenuViewHolder
import com.jkweyu.quickqr.view.home.holder.HomeEmptyViewHolder
import com.jkweyu.quickqr.view.home.holder.HomeMainViewHolder
import com.jkweyu.quickqr.view.home.holder.HomeMenuViewHolder
import com.jkweyu.quickqr.viewmodel.HomeItem
import com.jkweyu.quickqr.viewmodel.HomeRVItemViewModel
import java.util.Collections


class HomeMultiRVAdapter(
    var list: MutableList<HomeItem>,
    private val viewModel: HomeRVItemViewModel,
    private val myOwner: HomeFragment,
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),ItemMoveCallback.ItemTouchHelperContract,
    HomeMenuViewHolder.OnDeleteBTClickListener {
    var animatorMap : MutableMap<Long, ObjectAnimator?> = mutableMapOf()
    var items = mutableListOf<HomeItem>()
    init {
        setHasStableIds(true)

        items.add(0,HomeItem(itemType = VIEW_TYPE_MAIN))
        items.addAll(list)
        items.add(HomeItem(itemType = VIEW_TYPE_MENU, menuType = 2,title = "11111"))
        items.add(HomeItem(itemType = VIEW_TYPE_ADD_MENU))

        items.add(HomeItem(itemType = VIEW_TYPE_EMPTY))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            VIEW_TYPE_MAIN -> HomeMainViewHolder(ItemHomeMainLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            VIEW_TYPE_MENU -> HomeMenuViewHolder(ItemHomeMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel,myOwner,animatorMap,this)
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
                holder.binding.QRADDButton.setOnClickListener {
                }
                //QR 스캔하기 버튼
                holder.binding.QRSCANButton.setOnClickListener {
                }
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
    override fun getItemViewType(position: Int): Int {
        return items[position].itemType
    }
    private fun getItem(position: Int): HomeItem {
        return items[position]
    }
    // 아이템 제거 메서드
    private fun removeItem(position: Int,itemId : Long) {
        if (position in items.indices) {
            items.removeAt(position) // 데이터 삭제
            animatorMap.remove(itemId)?.end() // 모션 데이터 삭제
            notifyItemRemoved(position) // RecyclerView 업데이트
        }
        Log.d("checkAni","deleted ${itemId}: ${animatorMap}")
    }
    //드래그 인터페이스
    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        Log.d("onRowMid","before : ${items}")
        if(items[toPosition].itemType == VIEW_TYPE_MENU){
            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(items, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(items, i, i - 1)
                }
            }
            notifyItemMoved(fromPosition, toPosition)
            Log.d("onRowMid","after : ${items}")
        }

    }


    override fun getItemCount(): Int = items.size
    // 아이템 고유 ID 반환 메서드
    override fun getItemId(position: Int): Long {
        return if (position in items.indices) {
            items[position].hashCode().toLong()
        } else {
            -1L // 아이디를 찾지 못했을 때 반환되는 기본값
        }
    }

    override fun deleteBTClick(position: Int, itemId : Long) {
        removeItem(position,itemId)

    }

}
