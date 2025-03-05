package com.jkweyu.quickqr.view.FrameFragment

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstantsFrame
import com.jkweyu.quickqr.databinding.FragmentFrameBinding
import com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QRChoiceFragment
import com.jkweyu.quickqr.view.FrameFragment.QrCreateFragment.QRCreateFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel
import com.jkweyu.quickqr.viewmodel.MainViewModel


class FrameFragment: BaseFragment<FragmentFrameBinding>(R.layout.fragment_frame) {
    private lateinit var mainViewModel: MainViewModel
    private var frameFragmentViewModel: FrameFragmentViewModel? = null


    override fun initView() {
        binding.apply {

        }
    }



    private fun loadQrChoiceFragment(){
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_container, QRChoiceFragment(), "QRChoiceFragment")
            .commit()
    }
    private fun loadQrCreateFragment(){

        childFragmentManager.beginTransaction()
            .replace(R.id.frame_container, QRCreateFragment(), "QRCreateFragment")
            .commit()
    }
    private fun loadQrDetailFragment(){
        childFragmentManager.beginTransaction()
            .replace(R.id.frame_container, QrDetailFragment(null), "QrDetailFragment")
            .commit()
    }



    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        frameFragmentViewModel = ViewModelProvider(this@FrameFragment)[FrameFragmentViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        if (!hidden){
            if(mainViewModel.focusItem.value?.second != null){
                frameFragmentViewModel?.changeFragment(fragmentConstantsFrame.QR_DETAIL)
            }
            frameFragmentViewModel?.activityFragment?.observe(this@FrameFragment){fragment ->
                Log.d("focusTag","activityFragment")
                when(fragment){
                    fragmentConstantsFrame.QR_CHOICE -> {
                        loadQrChoiceFragment()
                    }
                    fragmentConstantsFrame.QR_CREATE -> {
                        loadQrCreateFragment()
                    }
                    fragmentConstantsFrame.QR_DETAIL -> {
                        loadQrDetailFragment()
                    }
                }
            }
//            if (mainViewModel.focusItem.value !=  null){
//                if(mainViewModel.focusEditItem.value != null){
//                    Log.d("focusTag","loadQrCreateFragment")
//                    loadQrCreateFragment()
//                }else{
//                    Log.d("focusTag","loadQrDetailFragment")
//                    loadQrDetailFragment()
//                }
//
//            }else{
//                Log.d("focusTag","frameFragmentViewModel")
//
//            }
        }else{
            viewModelStore.clear()
            frameFragmentViewModel = null
            mainViewModel.setFocusItem(null,null)
        }
    }
}