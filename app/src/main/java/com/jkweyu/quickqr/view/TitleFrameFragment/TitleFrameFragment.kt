package com.jkweyu.quickqr.view.TitleFrameFragment

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.FragmentTitleFrameBinding
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesQrCardFragment
import com.jkweyu.quickqr.view.MainFragment.favorites.subfragment.FavoritesQrItemFragment
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryQrCardFragment
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryQrItemFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.favorites.FavoritesRVItemViewModel
import com.jkweyu.quickqr.viewmodel.history.HistoryRVItemViewModel

class TitleFrameFragment: BaseFragment<FragmentTitleFrameBinding>(R.layout.fragment_title_frame) {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var backPressedCallback: OnBackPressedCallback
    override fun initView() {
        Log.d("onHiddenChanged","[[[[TitleFrameFragment]]]] initView")
        binding.apply {

            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(binding.toolbar)
            activity.supportActionBar?.apply {
                setDisplayShowTitleEnabled(false) // 타이틀 숨기기
                setDisplayHomeAsUpEnabled(true)   // 뒤로 가기 버튼 활성화
            }
            toolbar.setNavigationOnClickListener{
                mainViewModel.changeFragment(fragmentConstants.MAIN)
            }
//
//            val type = mainViewModel.allFragSelectedItem.value
//            binding.type = type

//

        }
    }



    fun loadFrameLayout(fragment: Fragment): Boolean {
        childFragmentManager.beginTransaction()
            .replace(R.id.FrameLayout, fragment)
            .commit()
        return true
    }
    private fun registerOnBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainViewModel.changeFragment(fragmentConstants.MAIN)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        if (!hidden){
            mainViewModel.setDepth(1)
            registerOnBackPressedCallback()
            val type = mainViewModel.allFragSelectedItem.value
            binding.type = type

            val historyViewModel = HistoryRVItemViewModel()
            val favoritesViewModel = FavoritesRVItemViewModel()

            when(type){
                "QR명함 제작 기록" -> {
                    loadFrameLayout(HistoryQrCardFragment())
                }
                "QR명함 즐겨찾기" -> {
                    loadFrameLayout(FavoritesQrCardFragment())
                }
                "QR페이지 제작 기록" -> {
                    loadFrameLayout(HistoryQrItemFragment())
                }
                "QR페이지 즐겨찾기" -> {
                    loadFrameLayout(FavoritesQrItemFragment())
                }
                else -> {

                }
            }
            Log.d("onHiddenChanged","[[[[TitleFrameFragment]]]] show")
        }else{
            Log.d("onHiddenChanged","[[[[TitleFrameFragment]]]] hide")
            if(::backPressedCallback.isInitialized){
                Log.d("onHiddenChanged","QRChoiceFragment onDetach backPressedCallback 해제")
                backPressedCallback.remove()
            }
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return if(item.itemId == android.R.id.home){
//            onBackPressedDispatcher.onBackPressed()
//            true
//        }else{
//            super.onOptionsItemSelected(item)
//        }
//    }

}