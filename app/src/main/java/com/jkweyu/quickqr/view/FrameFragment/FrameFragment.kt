package com.jkweyu.quickqr.view.FrameFragment

import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstantsFrame
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentFrameBinding
import com.jkweyu.quickqr.view.FrameFragment.QrChoiceFragment.QRChoiceFragment
import com.jkweyu.quickqr.view.FrameFragment.QrCreateFragment.QRCreateFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel
import com.jkweyu.quickqr.viewmodel.MainViewModel


class FrameFragment(private var item : QRCodeItem?): BaseFragment<FragmentFrameBinding>(R.layout.fragment_frame) {
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
            if (mainViewModel.focusItem.value !=  null){
                loadQrDetailFragment()
            }else{
                frameFragmentViewModel?.activityFragment?.observe(this@FrameFragment){fragment ->
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
            }
        }else{
            viewModelStore.clear()
            frameFragmentViewModel = null
            mainViewModel.setFocusItem(null)
        }
    }
}