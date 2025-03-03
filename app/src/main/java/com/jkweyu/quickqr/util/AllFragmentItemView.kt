package com.jkweyu.quickqr.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.databinding.ViewAllFragmentItemBinding


class AllFragmentItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        ViewAllFragmentItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        if (!isInEditMode && attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AllFragmentItemView)
            val icon = typedArray.getResourceId(R.styleable.AllFragmentItemView_icon, 0)
            binding.itemIcon.setImageResource(icon)

            val title = typedArray.getString(R.styleable.AllFragmentItemView_title)
            binding.itemTitle.text = title

            val content = typedArray.getString(R.styleable.AllFragmentItemView_count) // 여기 오타 수정 (title -> content)
            if (content == null) {
                binding.paymentImage.visibility = VISIBLE
            } else {
                binding.paymentImage.visibility = GONE
                binding.itemContent.text = content
            }

            setOnClickListener {
                Toast.makeText(context, "test : $title", Toast.LENGTH_SHORT).show()
            }

            typedArray.recycle()
        } else {
            // 편집기 모드일 때 기본값 설정
            binding.itemTitle.text = "샘플 제목"
            binding.itemContent.text = "샘플 내용"
            binding.itemIcon.setImageResource(android.R.drawable.ic_menu_info_details)
            binding.paymentImage.visibility = GONE
        }
    }
}
