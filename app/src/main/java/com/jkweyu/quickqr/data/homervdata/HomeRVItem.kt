package com.jkweyu.quickqr.data.homervdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class HomeRVItem(
    @PrimaryKey var rid : Long, // 기본값 설정 (실제 사용 시 계산됨)
    @ColumnInfo(name = "item_position") var itemPosition: Int?,
    @ColumnInfo(name = "item_type") var itemType: Int,
    @ColumnInfo(name = "item_title") var title: String,
    @ColumnInfo(name = "item_subTitle") var subTitle: String,
    @ColumnInfo(name = "item_content") var content: String,
    @ColumnInfo(name = "item_date") var date: Date
)

