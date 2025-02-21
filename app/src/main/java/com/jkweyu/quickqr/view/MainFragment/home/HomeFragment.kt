package com.jkweyu.quickqr.view.MainFragment.home

import android.content.ClipData
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.Util.BarcodeAnalysis
import com.jkweyu.quickqr.Util.BarcodeResultListener
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.FragmentHomeBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel
import kotlin.random.Random


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
//        Log.d("ssajkndna","${mainViewModel.fragmentDepth.value}")
        homeViewModel = ViewModelProvider(this@HomeFragment)[HomeRVItemViewModel::class.java]

        binding.apply {
//            //데이터 바인딩
            hViewModel = homeViewModel
//            lifecycleOwner = this@HomeFragment
//
//            itemAddButton.setOnClickListener {
//                /** itemADD 처리 및 데이터 저장
//                 *
//                 */
//            }
//
//            binding.itemSaveButton.setOnClickListener {
//                homeViewModel.toggleItemVisibilityOff()
//                mainViewModel.saveList()
//            }
//
//
//            homeViewModel.deleteItemPosition.observe(this@HomeFragment, Observer {
//                if(it != null){
//                    homeRecyclerview.adapter?.notifyItemRemoved(it)
//                }
//            })
//
            homeViewModel.selectedItem.observe(this@HomeFragment, Observer { item ->
                when(item?.itemType){
                    VIEW_TYPE_CREATE_QR -> {
                        mainViewModel.changeFragment(fragmentConstants.FRAME)

//                        var intent = Intent(this@HomeFragment.context, FrameActivity::class.java)
//                        startActivity(intent)
                    }
                    VIEW_TYPE_SCAN_QR -> {
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
                    VIEW_TYPE_MENU -> {
                        Toast.makeText(this@HomeFragment.context,"${item.itemID}",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        /** itemADD 처리 및 데이터 저장
                         *
                         */
                        /** itemADD 처리 및 데이터 저장
                         *
                         */
                        var addItem = HomeItem(itemID = Random.nextInt(0, 101).toLong(),itemType = VIEW_TYPE_MENU, menuType = 1,title = "item02")
                        homeViewModel.addTodo(addItem)
                        homeRecyclerview.adapter?.notifyItemInserted(homeViewModel.getInsertIndex())

                    }
                }

            })

            homeViewModel.itemVisibility.observe(this@HomeFragment, Observer { isVisible ->
                if(isVisible) {
                    binding.bottomView.isVisible = true
                }else{
                    binding.bottomView.isVisible = false
                }
            })
        }
    }

    override fun onBarcodeIntentDetected(intent: Intent?) {
        if(intent != null){
//            startActivity(intent)
        }
    }
    override fun onBarcodeClipBoardDetected(clip: ClipData){
//        val clipboard = getSystemService(requireContext(), ClipboardManager::class.java)
//        clipboard?.setPrimaryClip(clip)
//        Toast.makeText(requireContext(), "내용이 클립보드에 복사되었습니다", Toast.LENGTH_SHORT).show()
    }

}