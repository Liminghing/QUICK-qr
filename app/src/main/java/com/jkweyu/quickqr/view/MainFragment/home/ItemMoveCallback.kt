package com.jkweyu.quickqr.view.MainFragment.home

import android.animation.ObjectAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MENU
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel

class ItemMoveCallback(private val mAdapter: ItemTouchHelperContract, private val viewModel: HomeRVItemViewModel) :
    ItemTouchHelper.Callback() {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        var dragFlags = 0
        if(viewModel.itemVisibility.value == true){
            if(viewHolder.itemViewType == VIEW_TYPE_MENU){
                dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            }
        }
        return makeMovementFlags(dragFlags, 0)
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG && viewHolder != null) {
            // 드래그 시작 시 아이템 확대
            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.1f).apply {
                duration = 100
            }.start()
            ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.1f).apply {
                duration = 100
            }.start()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        // 드래그 종료 시 서서히 원래 크기로 복원
        ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 1.0f).apply {
            duration = 200
        }.start()
        ObjectAnimator.ofFloat(viewHolder.itemView, "scaleY", 1.0f).apply {
            duration = 200
        }.start()

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if(viewHolder.itemViewType == VIEW_TYPE_MENU){
            mAdapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
            return true
        }else{
            return false
        }
    }

    interface ItemTouchHelperContract {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
    }
}