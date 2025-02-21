package com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType

import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.itemTypeConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentQrDetailContentBinding
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.DetailCard.TextContentFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.DetailCard.UrlContentFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel

class QrDetailContentFragment(private var item : QRCodeItem?): BaseFragment<FragmentQrDetailContentBinding>(R.layout.fragment_qr_detail_content) {
//    private lateinit var qrTypeViewModel : QrCreateViewModel
    private lateinit var mainViewModel : MainViewModel
    override fun initView() {
        binding.apply {
//            qrTypeViewModel = ViewModelProvider(requireActivity())[QrCreateViewModel::class.java]
            mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

            if(mainViewModel.focusItem.value != null){
                setView(mainViewModel.focusItem.value!!)
            }
            if(item!= null){
//                setView(item!!)
            }else{
//                lifecycleScope.launch {
//                    var item = mainViewModel.loadItem(mainViewModel.detailItemId.value!!)
//                    tileText.text = item.title
//                    subText.text = item.subTitle
//                    contentText.text = item.content
//                    when(item.itemType){
//                        itemTypeConstants.QR_TYPE_TEXT -> {
//                            iconType.setImageResource(R.drawable.ic_icon_text)
//                            contentImage.visibility = View.GONE
//                            contentText.visibility = View.VISIBLE
//                        }
//                        itemTypeConstants.QR_TYPE_LINK -> {
//                            iconType.setImageResource(R.drawable.ic_icon_link)
//                            contentImage.visibility = View.VISIBLE
//                            contentText.visibility = View.GONE
//                            Log.d("parseOGMeta", "${item.content}")
//                            val ogData = OGMetaParser.parseOGMeta(item.content)
//                            ogData?.let { metadata ->
//                                // 썸네일 이미지 URL
//                                val thumbnailUrl = metadata.image
//
//                                // Glide나 Coil 등을 사용하여 이미지 로드
//                                Glide.with(contentImage.context)
//                                    .load(thumbnailUrl)
//                                    .error(R.drawable.ic_icon_link)
//                                    .into(contentImage)
//                            }
//                        }
//                    }
//                }
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
                    .replace(R.id.content_frame, TextContentFragment(item))
                    .commit()
            }
            itemTypeConstants.QR_TYPE_LINK -> {
                binding.iconType.setImageResource(R.drawable.ic_icon_link)
                childFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, UrlContentFragment(item))
                    .commit()
            }
        }
    }
}