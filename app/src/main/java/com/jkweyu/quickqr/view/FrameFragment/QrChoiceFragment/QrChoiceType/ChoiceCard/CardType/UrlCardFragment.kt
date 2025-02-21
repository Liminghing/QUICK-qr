package com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QrChoiceType.ChoiceCard.CardType

import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstantsFrame
import com.jkweyu.quickqr.constants.itemTypeConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentUrlCardBinding
import com.jkweyu.quickqr.view.FrameFragment.FrameFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel
import com.jkweyu.quickqr.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import java.net.MalformedURLException
import java.net.URL
import java.util.Date


class UrlCardFragment(): BaseFragment<FragmentUrlCardBinding>(R.layout.fragment_url_card) {
//    private lateinit var qrTypeViewModel : QrCreateViewModel
    private lateinit var mainViewModel : MainViewModel
    private lateinit var frameFragmentViewModel: FrameFragmentViewModel
    override fun initView() {
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val frameFragment = requireActivity()
            .supportFragmentManager
            .findFragmentByTag("frame_fragment_tag") as FrameFragment
        frameFragmentViewModel = ViewModelProvider(frameFragment)[FrameFragmentViewModel::class.java]
        binding.apply {
            button.setOnClickListener {
                if(areAllFieldsFilled(editTitle, editSubtitle, editUrlContent)){


                    /**
                     * 링크에 https,http가 아니 붙음
                     */
                    Log.d("checkLink","before url ${editUrlContent.text}")
                    val link = validateAndFormatUrl(editUrlContent.text.toString())
                    Log.d("checkLink","after url ${link}")
                    if (link != null) {
                        //
                        frameFragmentViewModel.changeFragment(fragmentConstantsFrame.QR_DETAIL)
                        //
                        lifecycleScope.launch {
                            mainViewModel.insertItem(
                                QRCodeItem(
                                    itemType = itemTypeConstants.QR_TYPE_LINK,
                                    title = editTitle.text.toString(),
                                    subTitle= editSubtitle.text.toString(),
                                    content = link,
                                    date = Date()
                                )
                            )
                        }
                    } else {
                        Toast.makeText(requireActivity(), "Invalid URL", Toast.LENGTH_SHORT).show()
                    }


                }else{
                    Toast.makeText(requireActivity(), "모든 입력란을 채워주세요!", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    fun validateAndFormatUrl(url: String): String? {
        return try {
            // URL에 스킴이 없으면 "https://" 추가
            val formattedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
                "https://$url"
            } else {
                url
            }

            // URL 객체 생성하여 유효성 검사
            URL(formattedUrl)
            formattedUrl // 유효하면 반환
        } catch (e: MalformedURLException) {
            null // 유효하지 않으면 null 반환
        }
    }


    fun areAllFieldsFilled(vararg editTexts: EditText): Boolean {
        return editTexts.all { it.text.toString().trim().isNotEmpty() }
    }
}