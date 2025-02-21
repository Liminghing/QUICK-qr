package com.jkweyu.quickqr.view.MainFragment.all.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.databinding.ViewAllFragmentItemBinding


class AllFragmentItem(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private var binding: ViewAllFragmentItemBinding = ViewAllFragmentItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        // attrs.xml에서 View의 속성 목록을 가져온다.
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AllFragmentItem)
        val icon = typedArray.getResourceId(R.styleable.AllFragmentItem_icon,0)
        binding.itemIcon.setImageResource(icon)

        val title = typedArray.getString(R.styleable.AllFragmentItem_title)
        binding.itemTitle.text = title

        val content = typedArray.getString(R.styleable.AllFragmentItem_count)
        if(content == null){
            binding.paymentImage.visibility = VISIBLE
        }else{
            binding.paymentImage.visibility = GONE
            binding.itemContent.text = content
        }
        setOnClickListener {
            Toast.makeText(context, "test : ${title}", Toast.LENGTH_SHORT).show()
        }
        typedArray.recycle()
    }
}