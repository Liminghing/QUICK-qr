package com.jkweyu.quickqr.view.home

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.databinding.ItemHomeQrLayoutBinding
import com.jkweyu.quickqr.viewmodel.HomeItem
import com.jkweyu.quickqr.viewmodel.HomeRVItemViewModel
import java.util.Collections


class HomeRvAdapter(
    private val items: MutableList<HomeItem>,
    private val viewModel: HomeRVItemViewModel,
    private val myOwner: HomeFragment
) : RecyclerView.Adapter<HomeRvAdapter.HomeViewHolder>(),ItemMoveCallback.ItemTouchHelperContract {
    init {
        setHasStableIds(true)
    }

    // 내부 ViewHolder 클래스
    var num : Long? = null
    private val animatorList = mutableListOf<ObjectAnimator?>()
    var animator : ObjectAnimator? = null
    inner class HomeViewHolder(private val binding: ItemHomeQrLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            setItemSize()
            viewModel.itemVisibility.observe(myOwner, Observer { isVisible ->
                setDeleteMode(isVisible,itemView)

            })
            //아이템 삭제 버튼 클릭리스너
            binding.deleteButton.setOnClickListener {
                removeItem(adapterPosition)
            }
        }
        fun bind(item: HomeItem, viewModel: HomeRVItemViewModel){
            //아이템 객체 수정모드 확인
            setDeleteMode(viewModel.itemVisibility.value!!,itemView)
            Log.d("checkAdapterPosition", "${adapterPosition}")
            //마지막 아이템 아이디 확인
            num = getItemId(items.size-1)


            //아이템 선택 리스너
            binding.itemCard.setOnClickListener {
                if (viewModel.itemVisibility.value == false) { // itemVisibility가 false일 때만 클릭 처리
                    if(item.title != null){
                        Toast.makeText(itemView.context, "${item.title}의 아이템 클릭됨", Toast.LENGTH_SHORT).show()
                        Log.d("checkRvCLick","${item.title}의 아이템 확인")
                    }else{
                        Log.d("checkRvCLick","아이템 생성")
                    }
                }
            }




//            //바인딩 작업
//            binding.item = item
//            binding.viewModel = viewModel
//            // 데이터 바인딩 즉시 실행
            binding.executePendingBindings()
        }


        //아이템 객체 수정모드 진입 메서드
        private fun setDeleteMode(isVisible : Boolean,itemView : View){

            if(adapterPosition > 0){
                if(num == getItemId(adapterPosition)){
                    binding.deleteButton.isVisible = false
                    //binding.textView.text = "1111"
                }else{
                    binding.deleteButton.isVisible = isVisible
                    startAnimator(adapterPosition)
                    //binding.textView.text = "2222"
                }
            }else{
                binding.deleteButton.isVisible = isVisible
                startAnimator(adapterPosition)
                //binding.textView.text = "3333"
            }
        }
        //아이템 객체 흔들림 모션 메서드
        private fun startAnimator(position: Int) {
            /*
            *
            * 흔들림 모션 중복 실행 안되도록 처리 및 모션 분리(start,stop)
            *
            *
            * */
            if(animatorList.size < items.size){
                //모션 설정
                if (viewModel.itemVisibility.value == true){
                    animator = ObjectAnimator.ofFloat(
                        itemView,
                        "rotation",
                        *floatArrayOf(if (position % 2 == 0) 1.5f else -1.5f, if (position % 2 == 0) -1.5f else 1.5f)
                    ).apply {
                        duration = 300
                        repeatCount = ObjectAnimator.INFINITE
                        repeatMode = ObjectAnimator.REVERSE
                        start()
                    }
                    //각 객체별 모션을 담음
                    animatorList.add(animator)
                }else{
                    ObjectAnimator.ofFloat(
                        itemView,
                        "rotation",
                        0f,0f
                    ).apply {
                        start()
                        cancel()
                    }
                    animator?.cancel()
                    animatorList.forEach { it?.cancel() }
                    animatorList.clear()
                }
            }
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

    // 레이아웃과 ViewHolder 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeQrLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    // ViewHolder에 바인딩
    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position],viewModel)
    }

    // 아이템 개수 반환
    override fun getItemCount(): Int = items.size

    //드래그 앤 드롭 인터페이스
    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if(toPosition != items.size-1){
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
        }
    }


    //아이템 고유ID 반환 메서드
    override fun getItemId(position: Int): Long {

        return items[position].hashCode().toLong()
    }

    // 아이템 제거 메서드
    private fun removeItem(position: Int) {
        if (position in items.indices) {
            items.removeAt(position) // 데이터 삭제
            animatorList.removeAt(position) // 데이터 삭제
            notifyItemRemoved(position) // RecyclerView 업데이트
            //notifyItemRangeChanged(position, items.size) // 위치 변경 처리
        }
    }
}