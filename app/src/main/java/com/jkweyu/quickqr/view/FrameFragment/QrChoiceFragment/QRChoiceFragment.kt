package com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.FragmentQrChoiceBinding
import com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QrChoiceType.ChoiceCard.CardQrFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel

class QRChoiceFragment(): BaseFragment<FragmentQrChoiceBinding>(R.layout.fragment_qr_choice) {
    private lateinit var mainViewModel: MainViewModel


    private lateinit var backPressedCallback: OnBackPressedCallback


    override fun initView() {

        binding.apply {
            //데이터 바인딩
            viewpager.adapter = QRChoicePagerAdapter(this@QRChoiceFragment)
            // 옆 페이지도 보이도록 설정
            viewpager.offscreenPageLimit = 1
            viewpager.setPageTransformer(MarginPageTransformer(20))

            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.apply {
                setDisplayShowTitleEnabled(false) // 타이틀 숨기기
                setDisplayHomeAsUpEnabled(true)   // 뒤로 가기 버튼 활성화
            }
            toolbar.setNavigationOnClickListener{
                mainViewModel.changeFragment(fragmentConstants.MAIN)
                onDetach()
            }


        }
    }
    private fun registerOnBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainViewModel.changeFragment(fragmentConstants.MAIN)
                onDetach()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainViewModel.setDepth(1)
        registerOnBackPressedCallback()
        Log.d("onHiddenChanged","QRChoiceFragment onAttach backPressedCallback 등록")
    }

    override fun onDetach() {
        super.onDetach()
        if(::backPressedCallback.isInitialized){
            Log.d("onHiddenChanged","QRChoiceFragment onDetach backPressedCallback 해제")
            backPressedCallback.remove()
        }
    }

}

class QRChoicePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments: List<Fragment>
    init {
        //fragments = listOf(CardQrFragment(), GeneralQrFragment(), ItemQrFragment())
        fragments = listOf(CardQrFragment())
    }
    override fun getItemCount(): Int = fragments.size// 페이지 개수

    override fun createFragment(position: Int): Fragment = fragments[position]
}
