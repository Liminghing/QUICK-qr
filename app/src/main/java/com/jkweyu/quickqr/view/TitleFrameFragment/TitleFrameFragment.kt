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
        Log.d("checkView","initView() 호출")

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
            registerOnBackPressedCallback()

            if(mainViewModel.allFragSelectedItem.value != null){
                mainViewModel.setDepth(1)
                binding.appBarLayout.setExpanded(true, false)
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

                    else -> {

                    }
                }
            }else{
                mainViewModel.setDepth(2)
                binding.appBarLayout.setExpanded(true, false)
                binding.type = getString(R.string.qr_tff_add_menu_toolbar_title)
                loadFrameLayout(HistoryFragment())
            }
        }else{
            Log.d("checkView","onHiddenChanged(is hidden) 호출")
            if(::backPressedCallback.isInitialized){
                backPressedCallback.remove()
            }
        }
    }
}