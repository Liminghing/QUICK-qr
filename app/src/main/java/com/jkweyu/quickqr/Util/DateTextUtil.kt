package com.jkweyu.quickqr.Util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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
}