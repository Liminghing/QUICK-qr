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
import java.net.URI
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
            // 입력된 URL 문자열 정리 (앞뒤 공백 제거)
            val trimmedUrl = url.trim()

            // 스킴 확인 - http 또는 https만 허용
            val urlWithScheme = if (!trimmedUrl.startsWith("http://") && !trimmedUrl.startsWith("https://")) {
                // ftp 등 다른 스킴이 있으면 null 반환
                if (trimmedUrl.contains("://")) {
                    return null
                }
                // 스킴이 없으면 "https://" 추가
                "https://$trimmedUrl"
            } else {
                // 잘못된 스킴(예: htp://) 체크
                val scheme = trimmedUrl.split("://")[0].lowercase()
                if (scheme != "http" && scheme != "https") {
                    return null
                }
                trimmedUrl
            }

            // URI 객체 생성
            val uri = URI(urlWithScheme)
            val host = uri.host ?: throw MalformedURLException("Host is null")

            // TLD가 없는 경우 체크 (최소한 하나의 점이 있어야 함)
            if (!host.contains(".")) {
                return null
            }

            // 유효한 TLD 체크 (간단히 구현 - 도메인에 점이 최소 하나 있고, 마지막 부분이 비어있지 않은지 확인)
            val hostParts = host.split(".")
            if (hostParts.size < 2 || hostParts.last().isEmpty()) {
                return null
            }

            // 호스트가 단순 도메인이고 www로 시작하지 않을 경우만 www 추가
            val formattedUrl = if (!host.startsWith("www.") &&
                host.split('.').size == 2 &&
                !urlWithScheme.substring(uri.scheme.length + 3).contains("/")) {
                urlWithScheme.replaceFirst("://", "://www.")
            } else {
                urlWithScheme
            }

            // URL 객체 생성하여 유효성 검사
            URL(formattedUrl)
            formattedUrl // 유효하면 반환
        } catch (e: Exception) {
            null // 유효하지 않으면 null 반환
        }
    }


    fun areAllFieldsFilled(vararg editTexts: EditText): Boolean {
        return editTexts.all { it.text.toString().trim().isNotEmpty() }
    }
}