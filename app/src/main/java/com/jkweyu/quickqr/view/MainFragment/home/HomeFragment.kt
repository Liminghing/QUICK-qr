package com.jkweyu.quickqr.view.MainFragment.home

import android.content.ClipData
import android.content.Intent
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.Util.BarcodeAnalysis
import com.jkweyu.quickqr.Util.BarcodeResultListener
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.constants.homeItemTypeConstants
import com.jkweyu.quickqr.databinding.FragmentHomeBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home), BarcodeResultListener {
    companion object {
        val VIEW_TYPE_MAIN = 0
        val VIEW_TYPE_CREATE_QR = 1
        val VIEW_TYPE_SCAN_QR = 2
        val VIEW_TYPE_MENU = 3
        val VIEW_TYPE_ADD_MENU = 4
        val VIEW_TYPE_EMPTY = 5
    }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeViewModel: HomeRVItemViewModel
    private lateinit var scanner: GmsBarcodeScanner
    override fun initView() {

        scanner = GmsBarcodeScanning.getClient(requireContext())
        //뷰모델 생성
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        homeViewModel = ViewModelProvider(this@HomeFragment)[HomeRVItemViewModel::class.java]

        binding.apply {


            hViewModel = homeViewModel
            mViewModel = mainViewModel
            lifecycleScope.launch {
                val item = mainViewModel.loadHomeRVList()
                mainViewModel.loadQRList()
                delay(500)
                if (item){
                    mainViewModel.loadStatus()
                    homeRecyclerview.adapter?.notifyDataSetChanged()
                }
            }



            qrCreateArea.setOnClickListener {
                mainViewModel.changeFragment(fragmentConstants.FRAME)
            }
            qrScanArea.setOnClickListener {
                scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        BarcodeAnalysis(this@HomeFragment).startScanning(barcode)
                    }
                    .addOnCanceledListener {
                        // 스캔 취소 시 처리할 로직
                    }
                    .addOnFailureListener { exception ->
                        Log.e("BarcodeScanner", "Scanning failed: ${exception.message}")
                    }
            }
            homeViewModel.selectedHItem.observe(this@HomeFragment, Observer { it ->
                when(it){
                    VIEW_TYPE_MENU -> {
                        /** 수정
                         *
                         */

                    }
                    VIEW_TYPE_ADD_MENU -> {
                        mainViewModel.changeFragment(fragmentConstants.TITLE_FRAME)
                    }
                    else -> {

                    }

                }
            })
            mainViewModel.qrCodeList.observe(this@HomeFragment,Observer{
                /** 수정
                 *
                 */
                Log.d("checkNewItem","homeFragment")
                homeRecyclerview.adapter?.notifyDataSetChanged()
            })

            mainViewModel.homeRvItemList.observe(this@HomeFragment,Observer{
                /** 수정
                 *
                 */
                homeRecyclerview.adapter?.notifyDataSetChanged()
            })


            binding.itemAddButton.setOnClickListener {
                homeViewModel.onHItemClicked(homeItemTypeConstants.VIEW_TYPE_ADD_MENU)
            }
            binding.itemSaveButton.setOnClickListener {
                homeViewModel.toggleItemVisibilityOff()
                mainViewModel.updateVmItem()
            }

            homeViewModel.itemVisibility.observe(this@HomeFragment, Observer { isVisible ->
                if(isVisible) {
                    binding.bottomView.isVisible = true
                }else{
                    binding.bottomView.isVisible = false
                }
            })
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){

        }else{
            homeViewModel.toggleItemVisibilityOff()
        }
    }

    override fun onBarcodeIntentDetected(intent: Intent?) {
        TODO("Not yet implemented")
    }

    override fun onBarcodeClipBoardDetected(clip: ClipData) {
        TODO("Not yet implemented")
    }
}