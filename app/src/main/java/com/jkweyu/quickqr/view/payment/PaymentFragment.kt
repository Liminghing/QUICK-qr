package com.jkweyu.quickqr.view.payment

import android.app.ActivityOptions
import android.content.Intent
import com.jkweyu.quickqr.QRDetailActivity
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentPaymentBinding

class PaymentFragment: BaseFragment<FragmentPaymentBinding>(R.layout.fragment_payment) {
    override fun initView() {
        binding.apply {
            var intent = Intent(this@PaymentFragment.context, QRDetailActivity::class.java)
            BarCodeButton.setOnClickListener {
                val options = ActivityOptions
                    .makeSceneTransitionAnimation(requireActivity(),BarCodeButton, "BarCode")
                intent.putExtra("type", "BarCode")
                startActivity(intent, options.toBundle())
            }
            QrCodeButton.setOnClickListener {
                val options = ActivityOptions
                    .makeSceneTransitionAnimation(requireActivity(),QrCodeButton, "QrCode")
                intent.putExtra("type", "QrCode")
                startActivity(intent, options.toBundle())
            }

        }
    }
}