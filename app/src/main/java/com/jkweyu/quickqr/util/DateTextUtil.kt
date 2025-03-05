package com.jkweyu.quickqr.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateTextUtil {
    @JvmStatic
    fun formatTime(date: Date): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format(date)
    }



    fun Date.toSimpleString(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(this)
    }
    fun Date.toItemString(locale: Locale = Locale.getDefault()): String {
        val pattern = when (locale.language) {
            "ko" -> "MM월 dd일" // 한국어
            "en" -> if (locale.country == "GB") "dd MMM" else "MMM d" // 영국식 vs 미국식
            "es" -> "d 'de' MMM" // 스페인어
            "fr" -> "d MMMM" // 프랑스어
            "pt" -> "d 'de' MMM" // 포르투갈어
            "vi" -> "d MMM" // 베트남어
            "de" -> "d. MMM" // 독일어
            else -> "MMM d" // 기본 영어(미국식)
        }
        val sdf = SimpleDateFormat(pattern, locale)
        return sdf.format(this)
    }
}