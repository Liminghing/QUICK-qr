package com.jkweyu.quickqr.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.jkweyu.quickqr.data.MainDatabase
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.data.homervdata.HomeRVItemDao
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db: MainDatabase
    private val homeRVDao: HomeRVItemDao
    init {
        db = MainDatabase.getDatabase(application)
        homeRVDao = db.homeRVItemDao()
    }


    private val _homeRVItemList = MediatorLiveData<MutableList<HomeItem>>()
    val homeRvItemList: LiveData<MutableList<HomeItem>> get() = _homeRVItemList



    suspend fun loadList(): Boolean {
        val type2List: MutableList<HomeItem>?
        return withContext(Dispatchers.IO) {
            val loadList: List<HomeRVItem> = homeRVDao.getAll()
            type2List = loadList.map {
                HomeItem(
                    itemID = it.rid,
                    itemType = it.itemType,
                    menuType = it.menuType,
                    title = it.title
                )
            }.toMutableList()
            withContext(Dispatchers.Main) {
                _homeRVItemList.value = type2List!!
            }
            true // 데이터를 성공적으로 로드한 경우 true 반환
        }
    }
    fun saveList() {
        viewModelScope.launch(Dispatchers.IO) {
            homeRVDao.deleteAll()
            val items = homeRvItemList.value?.map {
                HomeRVItem(
                    rid = it.itemID,
                    itemType = it.itemType,
                    menuType = it.menuType,
                    title = it.title
                )
            } as MutableList<HomeRVItem>
            homeRVDao.insertItems(items)
        }
    }
}