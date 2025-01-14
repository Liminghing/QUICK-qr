package com.jkweyu.quickqr.data.homervdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class HomeRVItem(
    @PrimaryKey val rid: Long,
    @ColumnInfo(name = "item_ID") var itemID : Long,
    @ColumnInfo(name = "recycler_Type") var itemType : Int,
    @ColumnInfo(name = "item_Type") var menuType: Int?,
    @ColumnInfo(name = "item_Title") var title: String?
)
