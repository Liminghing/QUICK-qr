package com.jkweyu.quickqr

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.databinding.ActivityDetailBinding
import com.jkweyu.quickqr.view.favorites.subfragment.FavoritesGeneralFragment
import com.jkweyu.quickqr.view.favorites.subfragment.FavoritesQrCardFragment
import com.jkweyu.quickqr.view.favorites.subfragment.FavoritesQrItemFragment
import com.jkweyu.quickqr.view.history.subfragment.HistoryGeneralFragment
import com.jkweyu.quickqr.view.history.subfragment.HistoryQrCardFragment
import com.jkweyu.quickqr.view.history.subfragment.HistoryQrItemFragment
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val type = intent.getStringExtra("type")
        binding.type = type
        val historyViewModel = HistoryRVItemViewModel()
        val favoritesViewModel = FavoritesRVItemViewModel()

        when(type){
            "QR명함 제작 기록" -> {
                loadFrameLayout(HistoryQrCardFragment(historyViewModel))
            }
            "QR명함 즐겨찾기" -> {
                loadFrameLayout(FavoritesQrCardFragment(favoritesViewModel))
            }
            "QR페이지 제작 기록" -> {
                loadFrameLayout(HistoryQrItemFragment(historyViewModel))
            }
            "QR페이지 즐겨찾기" -> {
                loadFrameLayout(FavoritesQrItemFragment(favoritesViewModel))
            }
            "일반QR 제작 기록" -> {
                loadFrameLayout(HistoryGeneralFragment(historyViewModel))
            }
            "일반QR 즐겨찾기" -> {
                loadFrameLayout(FavoritesGeneralFragment(favoritesViewModel))
            }
            else -> {

            }
        }

    }


    fun loadFrameLayout(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragment)
            .commit()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == android.R.id.home){
            onBackPressedDispatcher.onBackPressed()
            true
        }else{
            super.onOptionsItemSelected(item)
        }

    }



}
