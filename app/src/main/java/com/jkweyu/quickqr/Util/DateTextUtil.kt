package com.jkweyu.quickqr.Util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object DateTextUtil {
    @JvmStatic
    fun formatDateToKorean(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN)
        return localDateTime.format(formatter)
    }
    @JvmStatic
    fun formatTime(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return localDateTime.format(formatter)
    }



    fun Date.toSimpleString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(this)
    }
    fun Date.toItemString(): String {
        val sdf = SimpleDateFormat("MM월 dd일", Locale.getDefault())
        return sdf.format(this)
    }
}