package com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QrChoiceType.ChoiceCard.CardType

import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstantsFrame
import com.jkweyu.quickqr.constants.itemTypeConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentTextCardBinding
import com.jkweyu.quickqr.view.FrameFragment.FrameFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel
import com.jkweyu.quickqr.viewmodel.MainViewModel
import java.util.Date


class TextCardFragment(): BaseFragment<FragmentTextCardBinding>(R.layout.fragment_text_card) {
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
                if(areAllFieldsFilled(editTitle, editSubtitle, editTextContent)){
                    //
                    frameFragmentViewModel.changeFragment(fragmentConstantsFrame.QR_DETAIL)
                    //
                    mainViewModel.insertItem(
                        QRCodeItem(
                            itemType = itemTypeConstants.QR_TYPE_TEXT,
                            title = editTitle.text.toString(),
                            subTitle= editSubtitle.text.toString(),
                            content = editTextContent.text.toString(),
                            date = Date()
                        )
                    )
                }else{
                    Toast.makeText(requireActivity(), "모든 입력란을 채워주세요!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun areAllFieldsFilled(vararg editTexts: EditText): Boolean {
        return editTexts.all { it.text.toString().trim().isNotEmpty() }
    }
}