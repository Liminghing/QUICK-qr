package com.jkweyu.quickqr.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date


@Dao
interface QRCodeItemDao {

    @Query("SELECT * FROM qrcodeitem")
    fun getAll(): List<QRCodeItem>

    @Query("SELECT * FROM qrcodeitem WHERE rid = :itemId")
    fun loadByIds(itemId: Long): QRCodeItem


    @Query("SELECT * FROM qrcodeitem WHERE item_type LIKE :itemType " +
            "AND item_title LIKE :title " +
            "AND item_subTitle LIKE :subTitle " +
            "AND item_content LIKE :content " +
            "AND item_date LIKE :date " +
            "AND item_favorites LIKE :favorites " +
            "LIMIT 1")
    fun findByItem(itemType: Int?, title: String?, subTitle: String?,content: String?,date: Date?,favorites: Int?): QRCodeItem


    // 기존 아이템을 모두 삭제하는 메서드
    @Query("DELETE FROM qrcodeitem")
    fun deleteAll()

    @Update
    fun update(qrCodeItem: QRCodeItem): Int

    @Insert
    fun insertItems(mainItem: QRCodeItem) :Long

    @Delete
    fun delete(mainItem: QRCodeItem)
}
