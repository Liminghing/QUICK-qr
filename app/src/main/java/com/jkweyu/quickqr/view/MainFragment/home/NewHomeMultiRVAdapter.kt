package com.jkweyu.quickqr.view.MainFragment.home

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.ItemHomeAddMenuLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeEmptyLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMenuLayoutBinding
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_EMPTY
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeAddMenuViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeEmptyViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeMenuViewHolder
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


class NewHomeMultiRVAdapter(
    mViewModel: MainViewModel,
    private val viewModel: HomeRVItemViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemMoveCallback.ItemTouchHelperContract {
    var animatorMap : MutableMap<Long, ObjectAnimator?> = mutableMapOf()
    var items: MutableList<QRCodeItem>? = null
    init {
        items = mViewModel.gethPositionList()
        Log.d("onHiddenChangedInHomeFrag","items ${items}")
        setHasStableIds(true)
//        Log.d("updateItemupdateItemupdateItem","init ${items.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("updateItemupdateItemupdateItem","onCreateViewHolder ${items!!.size}")
        val context = parent.context
        return when (viewType) {
            VIEW_TYPE_MENU -> HomeMenuViewHolder(ItemHomeMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel,animatorMap)
            VIEW_TYPE_ADD_MENU -> HomeAddMenuViewHolder(ItemHomeAddMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
            VIEW_TYPE_EMPTY -> HomeEmptyViewHolder(ItemHomeEmptyLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            else -> throw IllegalArgumentException("Invalid view type ${items!!.size}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("updateItemupdateItemupdateItem","onBindViewHolder ${items!!.size}")
        when(holder){
            is HomeMenuViewHolder -> {
                holder.bind(items!![position])
            }
            is HomeAddMenuViewHolder -> {
                holder.bind()
                }
            is HomeEmptyViewHolder -> {
                holder.bind()
            }

        }
    }

    // 아이템 타입 반환 메서드
    override fun getItemViewType(position: Int): Int {
        // [1,2,3,4,5][+,Empty]
        // size 5
        // position 0,1,2,3,4
        // 0 < 5
        // 1 < 5
        // 2 < 5
        // 3 < 5
        // 4 < 5
        // 5 (+) < 5
        // 6 (Empty) < 5
        if(position < items!!.size){
            return VIEW_TYPE_MENU
        }else if(position == items!!.size){
            return VIEW_TYPE_ADD_MENU
        }else{
            return VIEW_TYPE_EMPTY
        }
    }

//    // 아이템 반환 메서드
//    private fun getItem(position: Int): HomeItem {
//        if(position < items.size){
//            return items[position]
//        }
////        when(val item = items[position]){
////            is HomeItem -> {
////                return item
////            }
////            is MutableList<*> -> {
////                return item[position] as HomeItem
////            }
////            else  -> {
////                throw IllegalArgumentException("Invalid view value")
////            }
////        }
//    }

    //드래그 인터페이스
    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
//        val fromItem = items[fromPosition] as HomeItem
//        val fromItemPosition = viewModel.getIndex(fromItem)
//        val toItem = items[toPosition] as HomeItem
//        val toItemPosition = viewModel.getIndex(toItem)
//        if(toItem.itemType == VIEW_TYPE_MENU){
//            if (fromPosition < toPosition) {
//                for (i in fromItemPosition until toItemPosition) {
////                    items.swap(i,i+1)
//                }
//            } else {
//                for (i in fromItemPosition downTo toItemPosition + 1) {
////                    items.swapList(i,i-1)
//                }
//            }
//            notifyItemMoved(fromPosition, toPosition)
//        }
    }

    // 아이템 개수 반환 메서
    override fun getItemCount(): Int = items!!.size + 2

    // 아이템 고유 ID 반환 메서드
//    override fun getItemId(position: Int): Long {
//        return items[position].itemID
////        return if (position in items.indices) {
////            items[position].hashCode().toLong()
////        } else {
////            -1L // 아이디를 찾지 못했을 때 반환되는 기본값
////        }
//    }
}


