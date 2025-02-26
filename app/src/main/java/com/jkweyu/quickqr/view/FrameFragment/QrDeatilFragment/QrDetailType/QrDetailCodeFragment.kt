package com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType

import android.graphics.Bitmap
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.Util.QrCodeUtil
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.itemTypeConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentQrDetailCodeBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel

class QrDetailCodeFragment(private var item : QRCodeItem?): BaseFragment<FragmentQrDetailCodeBinding>(R.layout.fragment_qr_detail_code) {
//    private lateinit var qrTypeViewModel : QrCreateViewModel
    private lateinit var mainViewModel : MainViewModel
    private var _qrBitmap: Bitmap? = null
    private val qrBitmap get() = _qrBitmap


    override fun initView() {
        binding.apply {
            mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
//            Log.d("dfgjkl","QrDetailCodeFragment ${mainViewModel.qrCodeList.value} ")

            if(mainViewModel.getFocusItem() != null){
                setView(mainViewModel.getFocusItem()!!)
            }

            downloadArea.setOnClickListener {
                try {
                    qrBitmap?.let { QrCodeUtil(requireActivity()).saveBitmapToGallery(it, "quickQR_${System.currentTimeMillis()}.jpg") }
                    Toast.makeText(requireContext(),"QR코드가 저장되었습니다.",Toast.LENGTH_SHORT).show()
                } catch (e: Exception){

                }
            }
            shareArea.setOnClickListener {
                //파일 프로바이더로 비트맵 이미지를 공유 받을 어플을 조회하는 다이얼로그 전시
                try {
                    QrCodeUtil(requireActivity()).shareQRCode(qrBitmap!!,"quickQR_${System.currentTimeMillis()}.jpg")
                } catch (e: Exception){
                    Toast.makeText(requireContext(),"QR코드를 공유할 수 없습니다.",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    private fun setView(item : QRCodeItem){
        _qrBitmap = QrCodeUtil(requireActivity()).generateQRCode(item.content)
        binding.qrCodeImage.setImageBitmap(qrBitmap)
        binding.tileText.text = item.title
        binding.subText.text = item.subTitle
        when(item.itemType){
            itemTypeConstants.QR_TYPE_TEXT -> binding.iconType.setImageResource(R.drawable.ic_icon_text)
            itemTypeConstants.QR_TYPE_LINK -> binding.iconType.setImageResource(R.drawable.ic_icon_link)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _qrBitmap?.recycle()
        _qrBitmap = null
    }


}