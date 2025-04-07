package com.jkweyu.quickqr.view.FrameFragment.QrTypePicker.Type

import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstantsFrame
import com.jkweyu.quickqr.databinding.FragmentChoiceLinkBinding
import com.jkweyu.quickqr.view.FrameFragment.FrameFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel


class ChoiceLinkFragment(): BaseFragment<FragmentChoiceLinkBinding>(R.layout.fragment_choice_link) {
    private lateinit var frameFragmentViewModel: FrameFragmentViewModel
    override fun initView() {
        binding.apply {



            val frameFragment = requireActivity()
                .supportFragmentManager
                .findFragmentByTag("frame_fragment_tag") as FrameFragment
            frameFragmentViewModel = ViewModelProvider(frameFragment)[FrameFragmentViewModel::class.java]

            button.setOnClickListener {
                frameFragmentViewModel.changeFragment(fragmentConstantsFrame.QR_CREATE)
                frameFragmentViewModel.setType(1)
            }
        }
    }
}
