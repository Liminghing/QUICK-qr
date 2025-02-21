package com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.DetailCard

import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentTextContentBinding


class TextContentFragment(private val item : QRCodeItem): BaseFragment<FragmentTextContentBinding>(R.layout.fragment_text_content) {

    override fun initView() {
        binding.apply {
            textContent.text = item.content
        }
    }
}