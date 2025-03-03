package com.jkweyu.quickqr.view.MainFragment.home

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.util.AutoIndexedList
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.databinding.ItemHomeAddMenuLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeEmptyLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMenuLayoutBinding
import com.jkweyu.quickqr.databinding.ItemHomeMenuShimmerLayoutBinding
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_EMPTY
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeAddMenuViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeEmptyViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeMenuViewHolder
import com.jkweyu.quickqr.view.MainFragment.home.holder.HomeShimmerViewHolder
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel


class NewHomeMultiRVAdapter(
    private val lists : AutoIndexedList<HomeRVItem>,
    private val mViewModel: MainViewModel,
    private val viewModel: HomeRVItemViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemMoveCallback.ItemTouchHelperContract {
    var animatorMap : MutableMap<Long, ObjectAnimator?> = mutableMapOf()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val context = parent.context
        return when (viewType) {
            0 -> HomeShimmerViewHolder(ItemHomeMenuShimmerLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            VIEW_TYPE_MENU -> HomeMenuViewHolder(ItemHomeMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),mViewModel,viewModel,animatorMap)
            VIEW_TYPE_ADD_MENU -> HomeAddMenuViewHolder(ItemHomeAddMenuLayoutBinding.inflate(LayoutInflater.from(context), parent, false),viewModel)
            VIEW_TYPE_EMPTY -> HomeEmptyViewHolder(ItemHomeEmptyLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(!mViewModel.isLoaded2.value!!){
            when(holder){
                is HomeShimmerViewHolder -> {
                    holder.bind()
                }
            }
        }else{
            when(holder){
                is HomeMenuViewHolder -> {
                    holder.bind(mViewModel.homeRvItemList.value?.get(position)!!)
                }
                is HomeAddMenuViewHolder -> {
                    holder.bind()
                }
                is HomeEmptyViewHolder -> {
                    holder.bind()
                }
            }
        }


    }

    override fun getItemId(position: Int): Long {
        if(!mViewModel.isLoaded2.value!!){
            return 1L
        }else{
            if(position < lists.size){
                return lists[position].rid
            }else if(position == lists.size){
                return -1L
            }else{
                return -1L
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

        if(!mViewModel.isLoaded2.value!!){
            return 0
        }else{
            if(position < lists.size){
                return VIEW_TYPE_MENU
            }else if(position == lists.size){
                return VIEW_TYPE_ADD_MENU
            }else{
                return VIEW_TYPE_EMPTY
            }
        }

//        if(position < lists.size){
//            return VIEW_TYPE_MENU
//        }else if(position == lists.size){
//            return VIEW_TYPE_ADD_MENU
//        }else{
//            return VIEW_TYPE_EMPTY
//        }
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
        if(lists.size > toPosition){
            val fromItem = lists[fromPosition]
            val fromItemPosition = lists.indexOfFirst { it == fromItem }

            val toItem = lists[toPosition]
            val toItemPosition = lists.indexOfFirst { it == toItem }

            if (fromPosition < toPosition) {
                Log.d("onRowMoved","${fromPosition}, ${toPosition}")
                for (i in fromItemPosition until toItemPosition) {
                    lists.swap(i,i+1)
                }
            } else {
                Log.d("onRowMoved","${fromPosition}, ${toPosition}")
                for (i in fromItemPosition downTo toItemPosition + 1) {
                    lists.swap(i,i-1)
                }
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    // 아이템 개수 반환 메서드
    override fun getItemCount(): Int {
        if(!mViewModel.isLoaded2.value!!){
            return 3
        }else{
            return lists.size + 2
        }
    }
}


