package com.jkweyu.quickqr.data.homervdata

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HomeRVItem::class], version = 1)
abstract class HomeRVItemDatabase : RoomDatabase() {
    abstract fun homeRVItemDao(): HomeRVItemDao
}