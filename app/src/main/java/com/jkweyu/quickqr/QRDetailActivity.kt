package com.jkweyu.quickqr

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.databinding.ActivityQrDetailBinding

class QRDetailActivity : BaseActivity<ActivityQrDetailBinding>(R.layout.activity_qr_detail) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        when(intent.getStringExtra("type")){
            "BarCode" -> {
                binding.BarCodeImg.visibility = View.VISIBLE
            }
            "QrCode" -> {
                binding.QrCodeImg.visibility = View.VISIBLE
            }
        }

        binding.cancelButton.setOnClickListener {
            finishAfterTransition()
        }


    }
}