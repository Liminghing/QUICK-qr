package com.jkweyu.quickqr.view.home.qrcreate.holder

import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentCardQrBinding
import com.jkweyu.quickqr.viewmodel.QrCreateViewModel

class CardQrFragment(): BaseFragment<FragmentCardQrBinding>(R.layout.fragment_card_qr) {
    private lateinit var qrTypeViewModel : QrCreateViewModel
    override fun initView() {
        binding.apply {
            qrTypeViewModel = ViewModelProvider(requireActivity())[QrCreateViewModel::class.java]
            button.setOnClickListener {
                qrTypeViewModel.checkQRType(0)
            }
            //데이터 바인딩
        }
    }
}
