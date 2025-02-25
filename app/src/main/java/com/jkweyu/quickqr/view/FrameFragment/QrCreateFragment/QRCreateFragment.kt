package com.jkweyu.quickqr.view.FrameFragment.QrCreateFragment

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstantsFrame
import com.jkweyu.quickqr.databinding.FragmentQrCreateBinding
import com.jkweyu.quickqr.view.FrameFragment.FrameFragment
import com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QrChoiceType.ChoiceCard.CardType.TextCardFragment
import com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QrChoiceType.ChoiceCard.CardType.UrlCardFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel
import com.jkweyu.quickqr.viewmodel.MainViewModel


class QRCreateFragment: BaseFragment<FragmentQrCreateBinding>(R.layout.fragment_qr_create) {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var frameFragmentViewModel: FrameFragmentViewModel

    private lateinit var backPressedCallback: OnBackPressedCallback
    override fun initView() {

        binding.apply {

            viewpager.adapter = QRCreatePagerAdapter(this@QRCreateFragment)
            // 옆 페이지도 보이도록 설정
            viewpager.offscreenPageLimit = 1
            viewpager.setPageTransformer(MarginPageTransformer(20))

            val activity = requireActivity() as AppCompatActivity
            activity.setSupportActionBar(toolbar)
            activity.supportActionBar?.apply {
                setDisplayShowTitleEnabled(false) // 타이틀 숨기기
                setDisplayHomeAsUpEnabled(true)   // 뒤로 가기 버튼 활성화
            }
            toolbar.navigationIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))
            toolbar.setNavigationOnClickListener{
                frameFragmentViewModel.changeFragment(fragmentConstantsFrame.QR_CHOICE)
                onDetach()
            }
        }
    }
    private fun registerOnBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                frameFragmentViewModel.changeFragment(fragmentConstantsFrame.QR_CHOICE)
                onDetach()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val frameFragment = requireActivity()
            .supportFragmentManager
            .findFragmentByTag("frame_fragment_tag") as FrameFragment
        frameFragmentViewModel = ViewModelProvider(frameFragment)[FrameFragmentViewModel::class.java]

        Log.d("focusTag","${mainViewModel.focusItem.value}")
        mainViewModel.setDepth(2)
        registerOnBackPressedCallback()
        Log.d("onHiddenChanged","QRCreateFragment onAttach backPressedCallback 등록")
    }

    override fun onDetach() {
        super.onDetach()
        if(::backPressedCallback.isInitialized){
            Log.d("onHiddenChanged","QRCreateFragment onDetach backPressedCallback 해제")
            backPressedCallback.remove()
        }
    }



}
class QRCreatePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    val fragments: List<Fragment>
    init {




        //fragments = listOf(CardQrFragment(), GeneralQrFragment(), ItemQrFragment())
        fragments = listOf(TextCardFragment(), UrlCardFragment())
    }
    override fun getItemCount(): Int = fragments.size // 페이지 개수

    override fun createFragment(position: Int): Fragment = fragments[position]
}
