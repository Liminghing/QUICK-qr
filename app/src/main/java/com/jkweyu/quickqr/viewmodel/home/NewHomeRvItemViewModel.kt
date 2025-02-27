package com.jkweyu.quickqr.viewmodel.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.jkweyu.quickqr.data.MainDatabase
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.data.homervdata.HomeRVItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewHomeRvItemViewModel(application: Application) : AndroidViewModel(application)  {
    private val homeRVDao: HomeRVItemDao
    init {
        val db = MainDatabase.getDatabase(application)
        homeRVDao = db.homeRVItemDao()
    }
    /**
     * _homeRVItemList
     */

//    fun saveList() {
//        viewModelScope.launch(Dispatchers.IO) {
//            homeRVDao.deleteAll()
//            val items = homeRvItemList.value?.map {
//                HomeRVItem(
//                    rid = it.itemID,
//                    itemType = it.itemType,
//                    menuType = it.menuType,
//                    title = it.title
//                )
//            } as MutableList<HomeRVItem>
//            homeRVDao.insertItems(items)
//        }
//    }
}