package com.jkweyu.quickqr.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException

class OGMetaParser {
    data class OGMetaData(
        val title: String? = null,
        val description: String? = null,
        val image: String? = null,
        val url: String? = null
    )

    companion object {
        private const val TIMEOUT_MILLIS = 10000

        /**
         * URL에서 OG 메타 데이터를 파싱합니다.
         * @param url 파싱할 웹페이지의 URL
         * @return OGMetaData 객체 또는 에러 발생시 null
         */
        suspend fun parseOGMeta(url: String): OGMetaData? {
            return try {
                // 백그라운드 스레드에서 네트워크 요청 수행
                withContext(Dispatchers.IO) {
                    val doc: Document = Jsoup.connect(url)
                        .timeout(TIMEOUT_MILLIS)
                        .get()

                    OGMetaData(
                        title = getMetaTagContent(doc, "og:title"),
                        description = getMetaTagContent(doc, "og:description"),
                        image = getMetaTagContent(doc, "og:image"),
                        url = getMetaTagContent(doc, "og:url")
                    )
                }
            } catch (e: IOException) {
                Log.e("OGMetaParser", "Error parsing OG meta tags", e)
                null
            }
        }

        /**
         * 특정 프로퍼티의 메타 태그 콘텐츠를 가져옵니다.
         */
        private fun getMetaTagContent(doc: Document, property: String): String? {
            return doc.select("meta[property=$property]").firstOrNull()?.attr("content")
        }
    }
}