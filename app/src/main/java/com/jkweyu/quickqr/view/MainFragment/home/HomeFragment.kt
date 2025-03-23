package com.jkweyu.quickqr.view.MainFragment.home

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.FragmentHomeBinding
import com.jkweyu.quickqr.util.BarcodeAnalysis
import com.jkweyu.quickqr.util.BarcodeResultListener
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.home.HomeRVItemViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException


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

    // 👉 이렇게 멤버 변수로 등록
    private val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // 사진 파일 uri
            val data: Intent? = result.data
            val imageUri: Uri? = data?.data
            // uri를 통해 InputImage 획득
            val image: InputImage
            try {
                image = InputImage.fromFilePath(requireContext(), imageUri!!)
                // BarcodeScanner 객체 획득
                val scanner = BarcodeScanning.getClient()
                // 이미지 프로세싱
                scanner.process(image)
                    .addOnSuccessListener { barcodes ->
                        barcodes.forEach { barcode ->
                            BarcodeAnalysis(this@HomeFragment).startScanning(barcode)
                        }
                    }
                    .addOnFailureListener {
                        // Task failed with an exception
                    }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
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
                val dialog = Dialog(requireContext())
                dialog.setContentView(R.layout.dialog_scan_type)
                val button01 = dialog.findViewById<ConstraintLayout>(R.id.qr_scan_camera)
                val button02 = dialog.findViewById<ConstraintLayout>(R.id.qr_scan_gallery)
                val cancelButton = dialog.findViewById<TextView>(R.id.cancel_button)
                button01.setOnClickListener {
                    scanner.startScan()
                        .addOnSuccessListener { barcode ->
                            BarcodeAnalysis(this@HomeFragment).startScanning(barcode)
                        }
                        .addOnCanceledListener {
                            // 스캔 취소 시 처리
                        }
                        .addOnFailureListener { exception ->
                            Log.e("BarcodeScanner", "Scanning failed: ${exception.message}")
                        }
                    dialog.dismiss()
                }
                button02.setOnClickListener {
                    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    requestLauncher.launch(galleryIntent)
                    dialog.dismiss()
                }
                cancelButton.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()
            }
            homeViewModel.selectedHItem.observe(this@HomeFragment, Observer { it ->
                when(it){
                    VIEW_TYPE_MENU -> {
                    }
                    VIEW_TYPE_ADD_MENU -> {
                        mainViewModel.changeFragment(fragmentConstants.TITLE_FRAME)
                    }
                    else -> {
                    }
                }
            })
            mainViewModel.qrCodeList.observe(this@HomeFragment,Observer{
                homeRecyclerview.adapter?.notifyDataSetChanged()
            })

            mainViewModel.homeRvItemList.observe(this@HomeFragment,Observer{
                homeRecyclerview.adapter?.notifyDataSetChanged()
            })


            binding.itemAddButton.setOnClickListener {
//                homeViewModel.onHItemClicked(homeItemTypeConstants.VIEW_TYPE_ADD_MENU)
            }
            binding.itemSaveButton.setOnClickListener {
//                homeViewModel.toggleItemVisibilityOff()
//                mainViewModel.updateVmItem()
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
        startActivity(intent)
    }

    override fun onBarcodeClipBoardDetected(clip: ClipData) {
        val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(clip)


        Toast.makeText(requireContext(), "클립보드에 복사했습니다", Toast.LENGTH_SHORT).show()
    }
}