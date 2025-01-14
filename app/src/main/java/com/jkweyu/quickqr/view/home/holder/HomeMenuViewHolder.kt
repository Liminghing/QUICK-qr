package com.jkweyu.quickqr.view.home.holder

import android.animation.ObjectAnimator
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHomeMenuLayoutBinding
import com.jkweyu.quickqr.view.home.HomeFragment
import com.jkweyu.quickqr.viewmodel.HomeItem
import com.jkweyu.quickqr.viewmodel.HomeRVItemViewModel

class HomeMenuViewHolder(
    val binding: ItemHomeMenuLayoutBinding,
    private val viewModel: HomeRVItemViewModel,
    private val myOwner: HomeFragment,
    private var animatorMap : MutableMap<Long, ObjectAnimator?>,
    private val listener:  OnDeleteBTClickListener

) : RecyclerView.ViewHolder(binding.root) {
    private var animator : ObjectAnimator? = null
    init {
        setItemSize()
        viewModel.itemVisibility.observe(myOwner, Observer { isVisible ->
            setDeleteMode(isVisible)
        })
    }
    fun bind(item: HomeItem) {
        //아이템 재활용시 수정모드 조회
        setDeleteMode(viewModel.itemVisibility.value!!)

        //아이템 아이디 확인용
        //binding.textView.text = itemId.toString()

        //아이템 삭제 버튼 클릭리스너
        binding.deleteButton.setOnClickListener {
            listener.deleteBTClick(adapterPosition,itemId)
        }
        //아이템 클릭시
        binding.itemCard.setOnClickListener {
            if(!viewModel.itemVisibility.value!!){
                Toast.makeText(itemView.context, "${item.itemID}인 ${item.title}이 클릭", Toast.LENGTH_SHORT).show()
            }
        }
        //바인딩 작업 수행
        binding.menuViewModel = viewModel
        binding.item = item



    }
    //아이템 객체 수정모드 진입 메서드
    private fun setDeleteMode(isVisible : Boolean){
        Log.d("kokokok","qkqkqk ${itemId}")
        if(itemId != -1L){
            if(isVisible){

                binding.deleteButton.isVisible = true
                startAnimator(adapterPosition,itemId)
            }else{
                Log.d("kokokok","qqqqq222")
                binding.deleteButton.isVisible = false
                stopAnimator(adapterPosition,itemId)
            }
        }
    }

    private fun stopAnimator(position: Int,itemId:Long) {
        animatorMap.remove(itemId)?.end()
        animatorMap.put(itemId,null)
    }
    //아이템 객체 흔들림 모션 메서드
    private fun startAnimator(position: Int,itemId:Long) {
        animatorMap.remove(itemId)?.cancel()
        //새로운 모션 객체 추가
        animatorMap.put(itemId,
            ObjectAnimator.ofFloat(
                itemView,
                "rotation",
                *floatArrayOf(
                    0f,
                    if (position % 2 == 0) 1f else -1f,
                    0f,
                    if (position % 2 == 0) -1f else 1f,
                    0f
                )
            ).apply {
                duration = 600
                repeatCount = ObjectAnimator.INFINITE
                interpolator = android.view.animation.LinearInterpolator()
                start()
            }
        )
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
    interface OnDeleteBTClickListener {
        fun deleteBTClick(position: Int,itemId: Long) // 클릭 이벤트 발생 시 호출될 메서드
    }
}