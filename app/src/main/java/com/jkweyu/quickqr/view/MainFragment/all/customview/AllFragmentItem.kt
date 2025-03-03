//package com.jkweyu.quickqr.view.MainFragment.all.customview
//
//import android.content.Context
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.widget.FrameLayout
//import android.widget.Toast
//import com.jkweyu.quickqr.R
//import com.jkweyu.quickqr.databinding.ViewAllFragmentItemBinding
//
//
//class AllFragmentItem(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : FrameLayout(context, attrs, defStyleAttr) {
//
//    private var binding: ViewAllFragmentItemBinding
//
//    init {
//        binding = ViewAllFragmentItemBinding.inflate(LayoutInflater.from(context), this, true)
//
//        if (!isInEditMode && attrs != null) {
//            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.allfragmentitem)
//            val icon = typedArray.getResourceId(R.styleable.allfragmentitem_icon, 0)
//            binding.itemIcon.setImageResource(icon)
//
//            val title = typedArray.getString(R.styleable.allfragmentitem_title)
//            binding.itemTitle.text = title
//
//            val content = typedArray.getString(R.styleable.allfragmentitem_count)
//            if (content == null) {
//                binding.paymentImage.visibility = VISIBLE
//            } else {
//                binding.paymentImage.visibility = GONE
//                binding.itemContent.text = content
//            }
//
//            setOnClickListener {
//                Toast.makeText(context, "test : ${title}", Toast.LENGTH_SHORT).show()
//            }
//
//            typedArray.recycle()
//        } else {
//            // 편집기 모드일 때 기본값 설정
//            binding.itemTitle.text = "샘플 제목"
//            binding.itemContent.text = "샘플 내용"
//            // 기본 아이콘 설정 (안드로이드 기본 아이콘 사용)
//            binding.itemIcon.setImageResource(android.R.drawable.ic_menu_info_details)
//            binding.paymentImage.visibility = GONE
//        }
//    }
//}