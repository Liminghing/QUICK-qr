package com.jkweyu.quickqr.view.FrameFragment.QrDetail

import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.itemTypeConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentQrDetailContentBinding
import com.jkweyu.quickqr.view.FrameFragment.QrDetail.Type.DetailLinkFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDetail.Type.DetailTextFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel

class QrDetailContentFragment(private var item : QRCodeItem?): BaseFragment<FragmentQrDetailContentBinding>(R.layout.fragment_qr_detail_content) {
//    private lateinit var qrTypeViewModel : QrCreateViewModel
    private lateinit var mainViewModel : MainViewModel
    override fun initView() {
        binding.apply {
            mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

            if(mainViewModel.getFocusItem() != null){
                setView(mainViewModel.getFocusItem()!!)
            }
        }
    }
    private fun setView(item : QRCodeItem){
        binding.tileText.text = item.title
        binding.subText.text = item.subTitle

        when(item.itemType){
            itemTypeConstants.QR_TYPE_TEXT -> {
                binding.iconType.setImageResource(R.drawable.ic_icon_text)
                childFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, DetailTextFragment(item))
                    .commit()
            }
            itemTypeConstants.QR_TYPE_LINK -> {
                binding.iconType.setImageResource(R.drawable.ic_icon_link)
                childFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, DetailLinkFragment(item))
                    .commit()
            }
        }
    }
}