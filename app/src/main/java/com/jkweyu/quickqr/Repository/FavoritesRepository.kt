package com.jkweyu.quickqr.Repository

import com.jkweyu.quickqr.model.favorites.FavoritesRVItem
import com.jkweyu.quickqr.view.history.HistoryFragment.Companion.VIEW_TYPE_HISTORY
import java.time.LocalDateTime

class FavoritesRepository {
    private lateinit var items : MutableList<FavoritesRVItem>

    fun getList() : MutableList<FavoritesRVItem>{
        items = mutableListOf(
            FavoritesRVItem(itemID = 11L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR1 Card", "Kakao1", LocalDateTime.of(2024, 11, 15, 20, 39)),
            FavoritesRVItem(itemID = 22L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR2 Card", "Kakao2", LocalDateTime.of(2024, 11, 15, 20, 39)),
            FavoritesRVItem(itemID = 33L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR3 Card", "Google3", LocalDateTime.of(2024, 11, 14, 18, 13)),
            FavoritesRVItem(itemID = 44L, itemType = VIEW_TYPE_HISTORY, qrType = 2, "QR4 Item", "Naver4", LocalDateTime.of(2024, 11, 13, 16, 31)),
            FavoritesRVItem(itemID = 55L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR5 Card", "Kakao5", LocalDateTime.of(2024, 11, 13, 20, 39)),
            FavoritesRVItem(itemID = 66L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR6 Card", "Google6", LocalDateTime.of(2024, 11, 12, 18, 13)),
            FavoritesRVItem(itemID = 77L, itemType = VIEW_TYPE_HISTORY, qrType = 2, "QR7 Item", "Naver7", LocalDateTime.of(2024, 11, 12, 16, 31)),
            FavoritesRVItem(itemID = 88L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR8 Card", "Kakao8", LocalDateTime.of(2024, 11, 10, 20, 39)),
            FavoritesRVItem(itemID = 99L, itemType = VIEW_TYPE_HISTORY, qrType = 1, "QR9 Card", "Google9", LocalDateTime.of(2024, 11, 3, 18, 13)),
            FavoritesRVItem(itemID = 110L, itemType = VIEW_TYPE_HISTORY, qrType = 2, "QR10 Item", "Naver10", LocalDateTime.of(2024, 11, 2, 16, 31)),
            FavoritesRVItem(itemID = 120L, itemType = VIEW_TYPE_HISTORY, qrType = 3, "Phone Num", "010-2827-3494", LocalDateTime.of(2024, 11, 2, 21, 31))
        )

        return items
    }

}
