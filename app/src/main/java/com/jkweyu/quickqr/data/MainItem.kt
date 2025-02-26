package com.jkweyu.quickqr.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.jkweyu.quickqr.constants.itemFavoritesConstants
import com.jkweyu.quickqr.constants.itemHomeConstants
import java.util.Date
import java.util.Objects
import kotlin.math.absoluteValue


@Entity
data class QRCodeItem(
    @PrimaryKey var rid: Long = 0L, // 기본값 설정 (실제 사용 시 계산됨)
    @ColumnInfo(name = "item_type") var itemType: Int,
    @ColumnInfo(name = "item_title") var title: String,
    @ColumnInfo(name = "item_subTitle") var subTitle: String,
    @ColumnInfo(name = "item_content") var content: String,
    @ColumnInfo(name = "item_date") var date: Date,
    @ColumnInfo(name = "item_favorites") var favorites: Int = itemFavoritesConstants.FALSE,
    @ColumnInfo(name = "item_hPosition") var hPosition: Int = itemHomeConstants.FALSE
)
{
    init {
        rid = generateRid()
    }

    @Ignore
    fun generateRid(): Long {
        val hash = Objects.hash(itemType, title, subTitle, content, date)
        return hash.toLong().absoluteValue // 음수 방지
    }
}