package com.jkweyu.quickqr.base

import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowInsets
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.jkweyu.quickqr.R

abstract class BaseActivity<T: ViewDataBinding>(@LayoutRes val layoutRes: Int)
    : AppCompatActivity() {
    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 초기화된 layoutResId로 DataBinding 객체 생성
        binding = DataBindingUtil.setContentView(this, layoutRes)
        // LiveData를 사용하기 위한 lifecycleOwner 지정
        binding.lifecycleOwner = this@BaseActivity

        binding.root.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.R)
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val statusBarHeight = getStatusBarHeight()
                val params = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = statusBarHeight
                binding.root.layoutParams = params
            }
        })

    }
    fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        return true
    }
    @RequiresApi(Build.VERSION_CODES.R)
    private fun getStatusBarHeight(): Int {
        return window.decorView.rootWindowInsets?.getInsets(WindowInsets.Type.statusBars())?.top ?: 0
    }
}