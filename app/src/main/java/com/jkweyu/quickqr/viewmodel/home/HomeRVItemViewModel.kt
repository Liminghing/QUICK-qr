package com.jkweyu.quickqr.viewmodel.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jkweyu.quickqr.util.HomeRVList
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_ADD_MENU
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_CREATE_QR
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_EMPTY
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_MAIN
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment.Companion.VIEW_TYPE_SCAN_QR
import java.util.Collections

class HomeRVItemViewModel() : ViewModel() {
    private val list : MutableList<HomeItem> = mutableListOf()
    private val _itemList = MutableLiveData<HomeRVList>()
    val itemList: LiveData<HomeRVList> get() = _itemList

    private var items : HomeRVList = HomeRVList(
        HomeItem(itemID = 11L,itemType = VIEW_TYPE_MAIN),
        HomeItem(itemID = 22L,itemType = VIEW_TYPE_CREATE_QR),
        HomeItem(itemID = 33L,itemType = VIEW_TYPE_SCAN_QR),
        list,
        HomeItem(itemID = 44L,itemType = VIEW_TYPE_ADD_MENU),
        HomeItem(itemID = 55L,itemType = VIEW_TYPE_EMPTY)
    )

    init {
        _itemList.value = items
    }

    fun getIndex(item : HomeItem) : Int{
        return list.indexOf(item)
    }
    fun getInsertIndex() : Int{
        val addMenuIndex = items.indexOfLast {
            it is HomeItem && it.itemType == VIEW_TYPE_ADD_MENU
        }
        return addMenuIndex-1
    }

    fun addTodo(item: HomeItem){
        val items = itemList.value
        items?.addItem(item)
        _itemList.value = items!!
    }

    fun swapList(i : Int, j : Int){
        Collections.swap(list, i, j)
    }

    private val _deleteItemPosition = MutableLiveData<Int>(null)
    val deleteItemPosition: LiveData<Int> get() = _deleteItemPosition

    fun removeItem(itemPosition : Int){
        _deleteItemPosition.value = itemPosition
        val items = itemList.value
        items?.removeAt(itemPosition)
        _itemList.value = items!!
    }
    private val _selectedHItem = MutableLiveData<Int>(null) // 선택된 아이템 저장
    val selectedHItem: LiveData<Int> get() = _selectedHItem

    fun onHItemClicked(item: Int) {
        _selectedHItem.value = item // 클릭된 아이템 전달
    }




    private val _itemVisibility = MutableLiveData<Boolean>(false)
    val itemVisibility: LiveData<Boolean> get() {
        return _itemVisibility
    }
    fun toggleItemVisibility(item : HomeRVItem) : Boolean {
        if(item.title != null){
            if(itemVisibility.value != true){
                _itemVisibility.value = true
            }
        }
        return true
    }
    fun toggleItemVisibilityOff() : Boolean {
        _itemVisibility.value = false
        return true
    }

}

data class HomeItem(var itemID : Long,var itemType : Int,var menuType: Int? = null, var title: String? = null)


