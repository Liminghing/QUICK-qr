package com.jkweyu.quickqr.view.MainFragment.home.holder

import android.animation.ObjectAnimator
import android.util.Log
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.ItemHomeMenuLayoutBinding
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel

class HomeMenuViewHolder(
    val binding: ItemHomeMenuLayoutBinding,
    private val viewModel: HomeRVItemViewModel,
    private var animatorMap : MutableMap<Long, ObjectAnimator?>
) : RecyclerView.ViewHolder(binding.root) {
    init {
        setItemSize()
        viewModel.itemVisibility.observe(itemView.context as LifecycleOwner, Observer { isVisible ->
            setDeleteMode(isVisible)
        })
    }
    fun bind(item: QRCodeItem) {
        //아이템 재활용시 수정모드 조회
        setDeleteMode(viewModel.itemVisibility.value!!)
        //아이템 아이디 확인용
        binding.titleText.text = item.title
        binding.deleteButton.setOnClickListener {

//            viewModel.removeItem(adapterPosition)
        }

        //아이템 클릭
        binding.itemCard.setOnClickListener {
//            viewModel.onItemClicked(item)
        }
        //바인딩 작업 수행
        binding.menuViewModel = viewModel
        binding.item = item
    }
    //아이템 객체 수정모드 진입 메서드
    private fun setDeleteMode(isVisible : Boolean){
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
}