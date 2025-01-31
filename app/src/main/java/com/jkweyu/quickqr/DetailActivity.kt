package com.jkweyu.quickqr

import android.os.Bundle
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.type = intent.getStringExtra("type")

        binding.backButton.setOnClickListener {
            finish()
        }
    }
}