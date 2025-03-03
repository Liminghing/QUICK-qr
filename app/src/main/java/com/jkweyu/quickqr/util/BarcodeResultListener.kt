package com.jkweyu.quickqr.util

import android.content.ClipData
import android.content.Intent

interface BarcodeResultListener {
    fun onBarcodeIntentDetected(intent: Intent?)
    fun onBarcodeClipBoardDetected(clip: ClipData)
}