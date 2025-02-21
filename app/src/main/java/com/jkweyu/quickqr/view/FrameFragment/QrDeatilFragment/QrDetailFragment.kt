package com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment

import android.content.Context
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentQrDetailBinding
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.QrDetailCodeFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.QrDetailContentFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel


class QrDetailFragment(private var item : QRCodeItem?): BaseFragment<FragmentQrDetailBinding>(R.layout.fragment_qr_detail) {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var backPressedCallback: OnBackPressedCallback
    override fun initView() {


        binding.apply {

            // 뷰페이저 기본 설정
            viewpager.adapter = DetailFragmentAdapter(requireActivity(),item)
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
        Log.d("onHiddenChanged","QrDetailFragment onAttach backPressedCallback 등록")
    }

    override fun onDetach() {
        super.onDetach()
        if(::backPressedCallback.isInitialized){
            Log.d("onHiddenChanged","QrDetailFragment onDetach backPressedCallback 해제")
            backPressedCallback.remove()
        }
    }
}
class DetailFragmentAdapter(activity: FragmentActivity,item : QRCodeItem?): FragmentStateAdapter(activity) {
    val fragments: List<Fragment>

    init {
        fragments = listOf(QrDetailContentFragment(item), QrDetailCodeFragment(item))

    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}