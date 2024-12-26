package com.jkweyu.quickqr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.databinding.ActivityMainBinding

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            enableEdgeToEdge()
            // 기본 화면 설정
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment())
                    .commit()
            }
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        loadFragment(HomeFragment())
                        true
                    }
                    R.id.navigation_history -> {
                        loadFragment(HistoryFragment())
                        true
                    }
                    R.id.navigation_payment -> {
                        loadFragment(PaymentFragment())
                        true
                    }
                    R.id.navigation_favorites -> {
                        loadFragment(FavoritesFragment())
                        true
                    }
                    R.id.navigation_all -> {
                        loadFragment(AllFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }
}