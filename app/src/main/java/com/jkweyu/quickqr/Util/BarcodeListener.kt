package com.jkweyu.quickqr.Util

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import android.provider.CalendarContract
import android.provider.Settings.ACTION_WIFI_ADD_NETWORKS
import android.provider.Settings.ACTION_WIFI_SETTINGS
import android.provider.Settings.EXTRA_WIFI_NETWORK_LIST
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.mlkit.vision.barcode.common.Barcode

class BarcodeAnalysis(private val listener: BarcodeResultListener) {
    fun startScanning(barcode: Barcode) {
        if (barcode.rawValue != null && isUPIUrl(barcode.rawValue.toString())) {
            handleUPIResult(barcode)
        }else{
            handleBarcodeResult(barcode)
        }
    }

    private fun handleUPIResult(barcode: Barcode) : Intent? {
        return Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(barcode.rawValue)
        }
    }
    private fun handleBarcodeResult(barcode: Barcode){
        when (barcode.valueType) {
            Barcode.TYPE_URL -> {
                val url = barcode.url?.url
                Log.d("QR_SCAN", "일반 QR 코드 (웹사이트): $url")
                url?.let {
                    // 웹 브라우저로 URL 열기
                    listener.onBarcodeIntentDetected(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
                }
            }
            Barcode.TYPE_WIFI -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    Log.d("QR_SCAN", "TYPE_WIFI: connectToWiFi")
                    listener.onBarcodeIntentDetected(connectToWiFi(barcode.wifi))
                } else {
                    Log.d("QR_SCAN", "TYPE_WIFI: openWiFiSettings")
                    listener.onBarcodeIntentDetected(Intent(ACTION_WIFI_SETTINGS))

                }
            }

            Barcode.TYPE_PHONE -> {
                val phoneNumber = barcode.phone?.number
                Log.d("QR_SCAN", "전화번호 QR 코드 감지: $phoneNumber")

                phoneNumber?.let {
                    // 전화 다이얼 화면 열기
                    listener.onBarcodeIntentDetected(Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$it")
                    })
                }
            }

            Barcode.TYPE_SMS -> {
                val message = barcode.sms?.message
                val phoneNumber = barcode.sms?.phoneNumber
                Log.d("QR_SCAN", "SMS QR 코드 감지: $message")

                // SMS 작성 화면 열기
                listener.onBarcodeIntentDetected(Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("smsto:${phoneNumber ?: ""}")
                    putExtra("sms_body", message)
                })
            }
            Barcode.TYPE_TEXT -> {
                val text = barcode.displayValue
                Log.d("QR_SCAN", "텍스트 QR 코드 감지: $text")
                listener.onBarcodeClipBoardDetected(ClipData.newPlainText("QR Code Text", text))
            }

            Barcode.TYPE_EMAIL -> {
                val email = barcode.email?.address
                val subject = barcode.email?.subject
                val body = barcode.email?.body
                Log.d("QR_SCAN", "이메일 QR 코드 감지: $email")

                // 이메일 작성 화면 열기
                listener.onBarcodeIntentDetected(Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, body)
                })
            }
            Barcode.TYPE_GEO -> {
                val latitude = barcode.geoPoint?.lat
                val longitude = barcode.geoPoint?.lng
                Log.d("QR_SCAN", "위치 정보 QR 코드 감지: 위도: $latitude, 경도: $longitude")

                // 지도 앱으로 위치 열기
                val geoUri = Uri.parse("geo:$latitude,$longitude")
                listener.onBarcodeIntentDetected(Intent(Intent.ACTION_VIEW, geoUri))
            }
            Barcode.TYPE_CALENDAR_EVENT -> {
                val event = barcode.calendarEvent
                Log.d("QR_SCAN", "캘린더 이벤트 QR 코드 감지: ${event?.description}")

                // 캘린더 이벤트 추가
                listener.onBarcodeIntentDetected(Intent(Intent.ACTION_INSERT).apply {
                    data = CalendarContract.Events.CONTENT_URI
                    putExtra(CalendarContract.Events.TITLE, event?.summary)
                    putExtra(CalendarContract.Events.DESCRIPTION, event?.description)
                    putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event?.start?.rawValue)
                    putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event?.end?.rawValue)
                    putExtra(CalendarContract.Events.EVENT_LOCATION, event?.location)
                })

            }

            else -> {
                val value = barcode.displayValue
                Log.d("QR_SCAN", "기타 QR 코드 감지: $value")
                listener.onBarcodeClipBoardDetected(ClipData.newPlainText("QR Code Value", value))

            }
        }
    }

    private fun isUPIUrl(data: String): Boolean {
        return data.startsWith("upi://", ignoreCase = true)
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun connectToWiFi(wifi: Barcode.WiFi?) : Intent? {
        if (wifi == null) return null

        val ssid = wifi.ssid ?: return null
        val password = wifi.password ?: ""
        val securityType = wifi.encryptionType

        val intent = Intent(ACTION_WIFI_ADD_NETWORKS).apply {
            putExtra(
                EXTRA_WIFI_NETWORK_LIST, arrayListOf(
                WifiNetworkSuggestion.Builder()
                    .setSsid(ssid)
                    .apply {
                        when (securityType) {
                            Barcode.WiFi.TYPE_WPA -> setWpa2Passphrase(password)
                            Barcode.WiFi.TYPE_WEP -> setWpa2Passphrase(password) // WEP는 WPA로 변경 권장
                            Barcode.WiFi.TYPE_OPEN -> setIsEnhancedOpen(true)
                        }
                    }
                    .build()
            ))
        }
        return intent
    }



}