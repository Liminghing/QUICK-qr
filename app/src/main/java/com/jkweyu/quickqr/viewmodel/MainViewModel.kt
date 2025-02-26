package com.jkweyu.quickqr.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jkweyu.quickqr.constants.activityBackgroundConstants
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.constants.itemHomeConstants
import com.jkweyu.quickqr.data.MainDatabase
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.data.QRCodeItemDao
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.data.homervdata.HomeRVItemDao
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db: MainDatabase
    private val homeRVDao: HomeRVItemDao
    private val qrCodeItemDao: QRCodeItemDao
    init {
        db = MainDatabase.getDatabase(application)
        homeRVDao = db.homeRVItemDao() // 삭제?
        qrCodeItemDao = db.qrCodeItemDao()
    }




    /**
     *
     */
    private val _fragmentDepth = MutableLiveData<Int>(0) // 선택된 아이템 저장
    val fragmentDepth: LiveData<Int> get() = _fragmentDepth

    fun setDepth(depth : Int) {
        _fragmentDepth.value = depth
    }
    // 값 1 증가 (addDepth)
    fun addDepth() {
        _fragmentDepth.value = _fragmentDepth.value?.plus(1)
    }

    // 값 1 감소 (removeDepth), 최소값 0 유지
    fun removeDepth() {
        _fragmentDepth.value = _fragmentDepth.value?.let {
            if (it > 0) it - 1 else 0
        }
    }

    /**
     *
     */
    private val _selectedItem = MutableLiveData<QRCodeItem>(null) // 선택된 아이템 저장
    val selectedItem: LiveData<QRCodeItem> get() = _selectedItem

    fun onItemClicked(item: QRCodeItem) {
        _selectedItem.value = item // 클릭된 아이템 전달
    }



    /**
     * qr 생성 화면에서 생성 버튼 클릭시 전환
     */
    private val _createQRType = MutableLiveData<Int>(0)
    val createQRType: LiveData<Int> get() = _createQRType

    fun checkQRType(type:Int) {
        _createQRType.value = type
    }

    private val _activityBackground = MutableLiveData<Int>(activityBackgroundConstants.WHITE)
    val activityBackground: LiveData<Int> get() = _activityBackground

    fun setActivityBackground(key : Int){
        _activityBackground.value = key
    }
    private val _allFragSelectedItem = MutableLiveData<String>(null) // 선택된 아이템 저장
    val allFragSelectedItem: LiveData<String> get() = _allFragSelectedItem

    fun onAllFragItemClicked(type: String) {
        _allFragSelectedItem.value = type // 클릭된 아이템 전달
    }

    /**
     *
     */
        private val _activityFragment = MediatorLiveData<Int>(fragmentConstants.MAIN)
    val activityFragment: LiveData<Int> get() = _activityFragment

    fun changeFragment(fragment : Int){
        _activityFragment.value = fragment
    }

    /**
     * QR 아이템 선택 ID
     */


    /**
     * QR 아이템 리스트
     */
    private val _qrCodeList = MediatorLiveData<MutableList<QRCodeItem>>()
    val qrCodeList: LiveData<MutableList<QRCodeItem>> get() = _qrCodeList

    // [뷰모델] 리스트 반환 함수
    fun getQRCodeList() : MutableList<QRCodeItem>{
        return qrCodeList.value ?: mutableListOf()
    }

    // [뷰모델] 리스트 반환 함수
    fun gethPositionList() : MutableList<QRCodeItem>{
        return qrCodeList.value?.filter { it.hPosition == itemHomeConstants.TRUE }?.toMutableList() ?: mutableListOf()
    }

    // [뷰모델] QR 코드 아이템 추가 함수
    fun addQRCodeItem(item: QRCodeItem) {
        val currentList = qrCodeList.value ?: mutableListOf()
        val newList = currentList.toMutableList() // 기존 리스트 복사
        newList.add(item) // 새로운 아이템 추가
        _qrCodeList.value = newList // LiveData 갱신
        Log.d("dfgjkl","MainViewModel ${qrCodeList.value}")
    }

    // [뷰모델] QR 코드 아이템 제거 함수 (선택 사항)
    fun removeQRCodeItem(item: QRCodeItem) {
        val currentList = _qrCodeList.value ?: mutableListOf()
        val newList = currentList.toMutableList()
        newList.remove(item)
        _qrCodeList.value = newList
    }

    // [뷰모델] QR 코드 아이템 제거 함수 (선택 사항)
    fun updateQRCodeItem(item: QRCodeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            qrCodeItemDao.update(item)
        }
        val currentList = _qrCodeList.value ?: mutableListOf()
        val newList = currentList.toMutableList()
        val index = newList.indexOfFirst { it.rid == item.rid }
        if (index != -1) {
            newList[index] = item
        }
        Log.d("onHiddenChangedInHomeFrag","ridList 업데이트}")
        _qrCodeList.value = newList
    }

    // [DB] DB의 모든 QR 리스트 로드
    suspend fun loadQRList(): Boolean {
        val type2List: MutableList<QRCodeItem>?
        return withContext(Dispatchers.IO) {
            val loadList: List<QRCodeItem> = qrCodeItemDao.getAll()
            type2List = loadList.map {
                QRCodeItem(
                    itemType = it.itemType,
                    title = it.title,
                    subTitle = it.subTitle,
                    content = it.content,
                    date = it.date,
                    favorites = it.favorites,
                    hPosition = it.hPosition
                )
            }.toMutableList()
            withContext(Dispatchers.Main) {
                _qrCodeList.value = type2List!!
            }
            true // 데이터를 성공적으로 로드한 경우 true 반환
        }
    }

    // [DB] DB의 특정 QR 아이템 반환
    suspend fun loadItem(id: Long): QRCodeItem {
        return withContext(Dispatchers.IO) {
            qrCodeItemDao.loadByIds(id)
        }
    }

    // [DB] DB의 특정 QR 아이템 추가 (이후 뷰모델 반영)
    fun insertItem(item : QRCodeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            qrCodeItemDao.insertItems(item)
        }
        addQRCodeItem(item)
        setFocusItem(item,"insertItem")
    }

    // [DB] DB의 특정 QR 아이템 삭제 (이후 뷰모델 반영)
    fun deleteItem(item : QRCodeItem) {
        viewModelScope.launch(Dispatchers.IO) {
            qrCodeItemDao.delete(item)
        }
        removeQRCodeItem(item)
    }


    //생성 및 선택시
    private val _focusItem = MutableLiveData<Pair<QRCodeItem?, String?>>(null) // 선택된 아이템 저장
    val focusItem: LiveData<Pair<QRCodeItem?, String?>> get() = _focusItem

    fun getFocusItem() : QRCodeItem? {
        return focusItem.value?.first
    }
    fun setFocusItem(item: QRCodeItem?, type: String?) {
        _focusItem.value = Pair(item,type) // 클릭된 아이템 전달
    }

    /**
     * _homeRVItemList
     */
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