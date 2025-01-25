package com.jkweyu.quickqr.model.favorites

import java.time.LocalDateTime

data class FavoritesRVItem(
    var itemID : Long,
    var itemType: Int,
    var qrType: Int? = null,
    var title: String? = null,
    var content: String? = null,
    var date: LocalDateTime? = null
)
