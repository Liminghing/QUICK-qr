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
                Log.d("parseOGMeta", "${item.content}")
                val ogData = OGMetaParser.parseOGMeta(item.content)
                ogData?.let { metadata ->
                    // 썸네일 이미지 URL
                    Log.d("parseOGMeta", "image ${metadata.image}")
                    Log.d("parseOGMeta", "title ${metadata.title}")
                    Log.d("parseOGMeta", "description ${metadata.description}")
                    val thumbnailUrl = metadata.image
                    ogTitle.text = metadata.title
                    ogDescription.text = metadata.description


                    Log.d("parseOGMeta", "$thumbnailUrl")


                    // Glide나 Coil 등을 사용하여 이미지 로드
                    Glide.with(ogImage.context)
                        .load(thumbnailUrl)
                        .centerCrop()
                        .error(R.color.unselected_color)
                        .into(ogImage)
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