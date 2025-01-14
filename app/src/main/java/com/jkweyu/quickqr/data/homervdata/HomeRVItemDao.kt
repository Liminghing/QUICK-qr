package com.jkweyu.quickqr.data.homervdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface HomeRVItemDao {

    @Query("SELECT * FROM homervitem")
    fun getAll(): List<HomeRVItem>

    @Query("SELECT * FROM homervitem WHERE rid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<HomeRVItem>

    @Query("SELECT * FROM homervitem WHERE item_ID LIKE :itemID " +
            "AND recycler_Type LIKE :itemType " +
            "AND item_Type LIKE :menuType " +
            "AND item_Title LIKE :title " +
            "LIMIT 1")
    fun findByItem(itemID : Long,itemType: Int, menuType: Int?, title: String?): HomeRVItem


    // 기존 아이템을 모두 삭제하는 메서드
    @Query("DELETE FROM homervitem")
    fun deleteAll()

    // 새로운 리스트를 삽입하는 메서드
    @Insert
    fun insertItems(items: List<HomeRVItem>)
}
