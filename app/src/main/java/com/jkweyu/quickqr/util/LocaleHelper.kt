package com.jkweyu.quickqr.util
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.Locale

//object LocaleHelper {
//    fun setLocale(context: Context, language: String): Context {
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val resources: Resources = context.resources
//        val config = Configuration(resources.configuration)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            config.setLocale(locale)
//            context.createConfigurationContext(config)
//        } else {
//            config.locale = locale
//            resources.updateConfiguration(config, resources.displayMetrics)
//        }
//
//        // 애플리케이션 컨텍스트 구성도 업데이트해야 함
//        (context.applicationContext as? Application)?.let {
//            val appResources = it.resources
//            val appConfig = Configuration(appResources.configuration)
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                appConfig.setLocale(locale)
//                it.createConfigurationContext(appConfig)
//            } else {
//                appConfig.locale = locale
//                appResources.updateConfiguration(appConfig, appResources.displayMetrics)
//            }
//        }
//
//        return context
//    }
//}
