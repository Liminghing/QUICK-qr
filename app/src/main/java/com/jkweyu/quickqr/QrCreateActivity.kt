package com.jkweyu.quickqr

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.databinding.ActivityQrCreateBinding
import com.jkweyu.quickqr.view.home.qrcreate.QRChoiceFragment
import com.jkweyu.quickqr.view.home.qrcreate.QRCreateFragment
import com.jkweyu.quickqr.viewmodel.QrCreateViewModel


class QrCreateActivity : BaseActivity<ActivityQrCreateBinding>(R.layout.activity_qr_create){
    private lateinit var qrTypeViewModel : QrCreateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        qrTypeViewModel = QrCreateViewModel()
        qrTypeViewModel = ViewModelProvider(this)[QrCreateViewModel::class.java]

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view, QRChoiceFragment())
            .commit()
        qrTypeViewModel.createQRType.observe(this,{ value ->
            Log.d("createQRType", "QR_TYPE: $value")
            when(value) {
                0 -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_view, QRCreateFragment())
                        .commit()
                }
                else ->{

                }
            }

        })


        // 초기 프래그먼트 : 생성할 QR 타입 선택 프래그먼트 전시 - qrcreate/.

        // 선택후 프래그먼트 : QR 생성 데이터 입력 프래그먼트 전시 - qrcreate/.

        // 완료후 프래그먼트 : QR 보여주는 프래그먼트(공용) 전시 - 공용프래그먼트

    }

}
