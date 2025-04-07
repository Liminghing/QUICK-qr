package com.jkweyu.quickqr.view.FrameFragment.QrTypePicker

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.FragmentQrChoiceBinding
import com.jkweyu.quickqr.view.FrameFragment.QrTypePicker.Type.ChoiceLinkFragment
import com.jkweyu.quickqr.view.FrameFragment.QrTypePicker.Type.ChoiceTextFragment
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
            //android:padding="@dimen/dp_16"

            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.margin_04)
            val screenWidth = resources.displayMetrics.widthPixels
            val pagerWidth = screenWidth - resources.getDimensionPixelOffset(R.dimen.margin_32)
            val offsetPx = screenWidth - pageMarginPx - pagerWidth

            viewpager.setPageTransformer { page, position ->
                page.translationX = position * -offsetPx
            }





            viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when(position){
                        0 ->
                            root.background = ContextCompat.getDrawable(requireContext(), R.color.qr_type01_color)
                        1 ->
                            root.background = ContextCompat.getDrawable(requireContext(), R.color.qr_type02_color)
                    }

                    Log.d("ViewPager", "현재 페이지: $position")
                }
            })

            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.apply {
                setDisplayShowTitleEnabled(false) // 타이틀 숨기기
                setDisplayHomeAsUpEnabled(true)   // 뒤로 가기 버튼 활성화
            }
            toolbar.navigationIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
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
//        fragments = listOf(CardQrFragment())
        fragments = listOf(ChoiceTextFragment(),ChoiceLinkFragment())
    }
    override fun getItemCount(): Int = fragments.size// 페이지 개수

    override fun createFragment(position: Int): Fragment = fragments[position]
}
