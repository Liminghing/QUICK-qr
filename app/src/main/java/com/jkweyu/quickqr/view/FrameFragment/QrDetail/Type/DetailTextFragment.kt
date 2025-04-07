package com.jkweyu.quickqr.view.FrameFragment.QrDetail.Type

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentTextContentBinding


class DetailTextFragment(private val item : QRCodeItem): BaseFragment<FragmentTextContentBinding>(R.layout.fragment_text_content) {

    override fun initView() {
        binding.apply {
            textContent.text = item.content
        }
    }
}