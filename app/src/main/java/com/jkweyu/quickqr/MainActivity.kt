package com.jkweyu.quickqr

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.databinding.ActivityMainBinding
import com.jkweyu.quickqr.view.all.AllFragment
import com.jkweyu.quickqr.view.favorites.FavoritesFragment
import com.jkweyu.quickqr.view.history.HistoryFragment
import com.jkweyu.quickqr.view.home.HomeFragment
import com.jkweyu.quickqr.view.payment.PaymentFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = MainViewModel(application)
        binding.button.setOnClickListener {
            Log.d("quickQRapp","mainViewModel의 리스트[${mainViewModel.homeRvItemList.value?.size}] ${mainViewModel.homeRvItemList.value}")
        }
        lifecycleScope.launch {
            val isLoaded = mainViewModel.loadList()
            if (isLoaded) {
                binding.apply {
                    enableEdgeToEdge()

                    if (mainViewModel.homeRvItemList.value != null) {
                        // 기본 화면 설정
                        if (savedInstanceState == null) {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container, HomeFragment(mainViewModel))
                                .commit()
                        }
                        bottomNavigationView.setOnItemSelectedListener { item ->
                            when (item.itemId) {
                                R.id.navigation_home -> {
                                    loadFragment(HomeFragment(mainViewModel))
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
        }
    }
}