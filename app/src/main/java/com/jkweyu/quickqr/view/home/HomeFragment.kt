package com.jkweyu.quickqr.view.home

import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHomeBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel
import kotlin.random.Random


class HomeFragment(private val mainViewModel: MainViewModel): BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    companion object {
        val VIEW_TYPE_MAIN = 0
        val VIEW_TYPE_CREATE_QR = 1
        val VIEW_TYPE_SCAN_QR = 2
        val VIEW_TYPE_MENU = 3
        val VIEW_TYPE_ADD_MENU = 4
        val VIEW_TYPE_EMPTY = 5
    }
    private lateinit var homeViewModel: HomeRVItemViewModel
    val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC)
        .build()

    override fun initView() {
        val scanner = GmsBarcodeScanning.getClient(requireContext())

        //뷰모델 생성
        homeViewModel = HomeRVItemViewModel(mainViewModel.homeRvItemList.value!!)
        binding.apply {
            //데이터 바인딩
            hViewModel = homeViewModel
            lifecycleOwner = this@HomeFragment

            itemAddButton.setOnClickListener {
                /** itemADD 처리 및 데이터 저장
                 *
                 */
            }

            binding.itemSaveButton.setOnClickListener {
                homeViewModel.toggleItemVisibilityOff()
                mainViewModel.saveList()
            }


            homeViewModel.deleteItemPosition.observe(this@HomeFragment, Observer {
                if(it != null){
                    homeRecyclerview.adapter?.notifyItemRemoved(it)
                }
            })

            homeViewModel.selectedItem.observe(this@HomeFragment, Observer { item ->
                when(item?.itemType){
                    VIEW_TYPE_CREATE_QR -> {
                        scanner.startScan()
                            .addOnSuccessListener { barcode ->
                                // Task completed successfully
                                val rawValue: String? = barcode.rawValue
                                Log.d("qqqqqqq",rawValue.toString())
                            }
                            .addOnCanceledListener {
                                // Task canceled
                                Log.d("qqqqqqq","addOnCanceledListener")
                            }
                            .addOnFailureListener { e ->
                                // Task failed with an exception
                                Log.d("qqqqqqq","addOnFailureListener")
                            }
                    }
                    VIEW_TYPE_SCAN_QR -> {
                        Toast.makeText(this@HomeFragment.context,"qr 생성하기}",Toast.LENGTH_SHORT).show()
                    }
                    VIEW_TYPE_MENU -> {
                        Toast.makeText(this@HomeFragment.context,"${item.itemID}",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
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

}