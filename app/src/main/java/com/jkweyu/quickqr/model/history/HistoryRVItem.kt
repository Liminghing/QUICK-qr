package com.jkweyu.quickqr.model.history

import java.time.LocalDateTime


data class HistoryRVItem(
    var itemID : Long,
    var itemType: Int,
    var qrType: Int? = null,
    var title: String? = null,
    var content: String? = null,
    var date: LocalDateTime? = null
)
