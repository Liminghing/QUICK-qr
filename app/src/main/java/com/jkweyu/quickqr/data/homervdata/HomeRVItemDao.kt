package com.jkweyu.quickqr.data.homervdata

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.Date

@Dao
interface HomeRVItemDao {

    @Query("SELECT * FROM homervitem ORDER BY item_position ASC")
    fun getAll(): List<HomeRVItem>

    @Query("SELECT * FROM homervitem WHERE rid = :itemId")
    fun loadByIds(itemId: Long): HomeRVItem?


    @Query("SELECT * FROM homervitem WHERE item_position LIKE :itemPosition " +
            "AND item_type LIKE :itemType " +
            "AND item_title LIKE :title " +
            "AND item_subTitle LIKE :subTitle " +
            "AND item_content LIKE :content " +
            "AND item_date LIKE :date " +
            "LIMIT 1")
    fun findByItem(itemPosition: Int?, itemType: Int?, title: String?, subTitle: String?, content: String?, date: Date?): HomeRVItem


    // 기존 아이템을 모두 삭제하는 메서드
    @Query("DELETE FROM homervitem")
    fun deleteAll()

    @Update
    fun update(homeRVItem: HomeRVItem): Int

    @Insert
    fun insertItem(mainItem: HomeRVItem) :Long

    @Insert
    fun insertList(items: List<HomeRVItem>)

    @Delete
    fun delete(mainItem: HomeRVItem)
}
