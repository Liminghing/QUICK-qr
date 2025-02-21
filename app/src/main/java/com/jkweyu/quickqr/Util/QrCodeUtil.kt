package com.jkweyu.quickqr.Util

import android.content.ClipData
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream

class QrCodeUtil(private val context: Context) {
    fun saveBitmapToGallery(bitmap: Bitmap, fileName: String) {
        // ContentValues 객체를 생성하여 저장할 이미지의 메타데이터를 정의
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일명
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") // 파일 타입

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1) // Android 10 이상에서 사용
                // RELATIVE_PATH를 지정하면 갤러리 내의 특정 폴더에 저장할 수 있음
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
            }
        }

        // ContentResolver를 통해 이미지를 저장할 URI 생성
        val imageUri = context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )

        // URI를 통해 출력 스트림을 열어 비트맵 데이터 쓰기
        imageUri?.let { uri ->
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                // 비트맵을 JPEG 형식으로 압축하여 저장
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Android 10 이상에서는 저장이 완료되었음을 표시
                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                context.contentResolver.update(uri, contentValues, null, null)
            }
        }
    }
    fun shareQRCode(bitmap: Bitmap, fileName: String){
        Log.d("testShare","shareQRCode 호출")
        val imageFolder = File(context.cacheDir,"images")
        Log.d("testShare","${imageFolder}")
        imageFolder.mkdirs()

        val file = File(imageFolder,fileName)

        FileOutputStream(file).use { stream ->
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
        }

        val contentUri = FileProvider.getUriForFile(context, "com.jkweyu.quickqr.fileprovider", file)
        Log.d("testShare","${contentUri}")

        // 미리보기용 작은 이미지 생성
        val thumbnailBitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, contentUri)
            // 미리보기 이미지 추가
            putExtra(Intent.EXTRA_TITLE, "QR 코드")
            putExtra(Intent.EXTRA_TEXT, "QR 코드를 공유합니다")

            // API 29+ 기기에서는 ClipData로 미리보기 이미지 지정 가능
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val clip = ClipData.newUri(context.contentResolver, "QR Code", contentUri)
                clipData = clip
            }

            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/png"
        }

        context.startActivity(Intent.createChooser(shareIntent, "QR코드 공유"))
    }

    fun generateQRCode(text: String, size: Int = 500): Bitmap? {
        return try {
            val barcodeEncoder = BarcodeEncoder()
            val bitMatrix: BitMatrix = barcodeEncoder.encode(text, BarcodeFormat.QR_CODE, size, size)
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

}