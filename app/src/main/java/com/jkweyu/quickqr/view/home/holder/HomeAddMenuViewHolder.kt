package com.jkweyu.quickqr.view.home.holder

import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHomeAddMenuLayoutBinding
import com.jkweyu.quickqr.viewmodel.HomeItem
import com.jkweyu.quickqr.viewmodel.HomeRVItemViewModel


class HomeAddMenuViewHolder(
    private val binding: ItemHomeAddMenuLayoutBinding,
    private val viewModel: HomeRVItemViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    init {

        setItemSize()
    }
    fun bind(item: HomeItem) {
        binding.itemCard.setOnClickListener {
            Toast.makeText(itemView.context, "${item.title}의 아이템 추가하기", Toast.LENGTH_SHORT).show()
        }
        binding.menuViewModel = viewModel
    }
    //아이템 객체 크기조정 메서드
    private fun setItemSize(){
        //뷰가 레이아웃을 완료한 후에 크기를 정확하게 가져오기
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width = binding.root.width
                if (width > 0) {
                    binding.root.layoutParams.height = width
                    binding.root.requestLayout()
                }
                // 리스너를 제거하여 반복 실행되지 않도록 합니다.
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }
}