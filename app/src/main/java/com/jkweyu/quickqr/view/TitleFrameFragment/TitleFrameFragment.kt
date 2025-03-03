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
import com.jkweyu.quickqr.view.MainFragment.history.HistoryFragment
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryQrCardFragment
import com.jkweyu.quickqr.view.MainFragment.history.subfragment.HistoryQrItemFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel

class TitleFrameFragment: BaseFragment<FragmentTitleFrameBinding>(R.layout.fragment_title_frame) {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var backPressedCallback: OnBackPressedCallback
    override fun initView() {

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
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



        if (!hidden){
            mainViewModel.setDepth(1)
            registerOnBackPressedCallback()

            if(mainViewModel.allFragSelectedItem.value != null){
                when(mainViewModel.allFragSelectedItem.value){
                    0 -> {
                        binding.type = getString(R.string.qr_tff_text_history_toolbar_title)
                        loadFrameLayout(HistoryQrCardFragment())
                    }
                    1 -> {
                        binding.type = getString(R.string.qr_tff_text_favorites_toolbar_title)
                        loadFrameLayout(FavoritesQrCardFragment())
                    }
                    2 -> {
                        binding.type = getString(R.string.qr_tff_link_history_toolbar_title)
                        loadFrameLayout(HistoryQrItemFragment())
                    }
                    3 -> {
                        binding.type = getString(R.string.qr_tff_link_favorites_toolbar_title)
                        loadFrameLayout(FavoritesQrItemFragment())
                    }
//                    4 -> {
//                        binding.type = "언어 설정"
//                        loadFrameLayout(LanguageFragment())
//                    }
                    else -> {

                    }
                }
            }else{
                binding.type = getString(R.string.qr_tff_add_menu_toolbar_title)
                loadFrameLayout(HistoryFragment())
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