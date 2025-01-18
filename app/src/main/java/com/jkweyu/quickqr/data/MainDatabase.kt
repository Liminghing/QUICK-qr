package com.jkweyu.quickqr.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.data.homervdata.HomeRVItemDao

//@Database(entities = [User::class, Post::class], version = 1)
@Database(entities = [HomeRVItem::class], version = 1)

abstract class MainDatabase : RoomDatabase() {
    abstract fun homeRVItemDao(): HomeRVItemDao
    companion object {
        // Volatile: 이 변수의 값을 다른 스레드에서 변경할 수 있도록 보장
        @Volatile
        private var INSTANCE: MainDatabase? = null

        // getDatabase 메서드 (싱글턴 패턴)
        fun getDatabase(context: Context): MainDatabase {
            // INSTANCE가 null이면 데이터베이스 인스턴스를 생성
            return INSTANCE ?: synchronized(this) {
                // 이미 인스턴스가 생성되었는지 체크하고, 없으면 새로 생성
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "home_rv_item_database"
                ).build()

                // 인스턴스가 null인 경우에만 새로 할당
                INSTANCE = instance
                instance
            }
        }
    }
}