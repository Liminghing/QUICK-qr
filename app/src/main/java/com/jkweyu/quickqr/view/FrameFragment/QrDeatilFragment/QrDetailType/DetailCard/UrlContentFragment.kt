package com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.DetailCard

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.Util.OGMetaParser
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentUrlContentBinding
import kotlinx.coroutines.launch


class UrlContentFragment(private val item : QRCodeItem): BaseFragment<FragmentUrlContentBinding>(R.layout.fragment_url_content) {
    override fun initView() {
        binding.apply {
            lifecycleScope.launch {
                val ogData = OGMetaParser.parseOGMeta(item.content)
                if (ogData != null) {
                    val thumbnailUrl = ogData.image
                    ogTitle.text = ogData.title ?: item.title
                    ogDescription.text = ogData.description ?: item.content
                    // Glide나 Coil 등을 사용하여 이미지 로드
                    Glide.with(ogImage.context)
                        .load(thumbnailUrl ?:R.drawable.ic_unload_og_img)
                        .centerCrop()
                        .error(R.color.unselected_color)
                        .into(ogImage)
                }else{
                    ogTitle.text = item.title
                    ogDescription.text = item.content
                    ogImage.setImageResource(R.drawable.ic_unload_og_img)
                }
            }
            ConstraintLayout.setOnClickListener {
                val myUri = Uri.parse(item.content)
                Log.d("parseOGMeta", "${myUri}")
                val intent = Intent(Intent.ACTION_VIEW, myUri)
                requireActivity().startActivity(intent)
            }
        }
    }
}