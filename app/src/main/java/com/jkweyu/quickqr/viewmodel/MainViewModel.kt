package com.jkweyu.quickqr.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jkweyu.quickqr.Util.AutoIndexedList
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.data.MainDatabase
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.data.QRCodeItemDao
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.data.homervdata.HomeRVItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db: MainDatabase
    private val qrCodeItemDao: QRCodeItemDao
    private val homeRVDao: HomeRVItemDao
    init {
        db = MainDatabase.getDatabase(application)
        qrCodeItemDao = db.qrCodeItemDao()
        homeRVDao = db.homeRVItemDao()
    }





    // 이동용 depth
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



//
//    /**
//     *
//     */
//    private val _selectedItem = MutableLiveData<QRCodeItem>(null) // 선택된 아이템 저장
//    val selectedItem: LiveData<QRCodeItem> get() = _selectedItem
//
//    fun onItemClicked(item: QRCodeItem) {
//        _selectedItem.value = item // 클릭된 아이템 전달
//    }



//    /**
//     * qr 생성 화면에서 생성 버튼 클릭시 전환
//     */
//    private val _createQRType = MutableLiveData<Int>(0)
//    val createQRType: LiveData<Int> get() = _createQRType
//
//    fun checkQRType(type:Int) {
//        _createQRType.value = type
//    }

//    private val _activityBackground = MutableLiveData<Int>(activityBackgroundConstants.WHITE)
//    val activityBackground: LiveData<Int> get() = _activityBackground
//
//    fun setActivityBackground(key : Int){
//        _activityBackground.value = key
//    }

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

    private val _isLoaded1 = MutableLiveData<Boolean>(false) // 선택된 아이템 저장
    val isLoaded1: LiveData<Boolean> get() = _isLoaded1

    fun loadStatus1(){
        _isLoaded1.value = true
    }

    private val _qrCodeList = MediatorLiveData<MutableList<QRCodeItem>>()
    val qrCodeList: LiveData<MutableList<QRCodeItem>> get() = _qrCodeList

    // [뷰모델] 리스트 반환 함수
    fun getQRCodeList() : MutableList<QRCodeItem>{
        return qrCodeList.value ?: mutableListOf()
    }

    // [뷰모델] QR 코드 아이템 추가 함수
    fun addQRCodeItem(item: QRCodeItem) {
        val currentList = qrCodeList.value ?: mutableListOf()
        val newList = currentList.toMutableList() // 기존 리스트 복사
        newList.add(item) // 새로운 아이템 추가
        _qrCodeList.value = newList // LiveData 갱신
        Log.d("dfgjkl","MainViewModel ${qrCodeList.value}")
    }

    // [뷰모델] QR 코드 아이템 제거 함수
    fun removeQRCodeItem(item: QRCodeItem) {
        val currentList = _qrCodeList.value ?: mutableListOf()
        val newList = currentList.toMutableList()
        newList.remove(item)
        _qrCodeList.value = newList

        val index =  vmList.indexOfFirst { it.rid == item.rid }



        removeItem(item)
        updateVmItem()
    }

    // [뷰모델] QR 코드 아이템 업데이트 함수
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
                    favorites = it.favorites
                )
            }.toMutableList()
            withContext(Dispatchers.Main) {
                _qrCodeList.value = type2List!!
            }
            true // 데이터를 성공적으로 로드한 경우 true 반환
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

    // [DB] DB의 특정 QR 아이템 반환
    suspend fun loadItem(id: Long): QRCodeItem {
        return withContext(Dispatchers.IO) {
            qrCodeItemDao.loadByIds(id)
        }
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
    fun findFocusItem(item: HomeRVItem?, type: String?) {
        val focusedItem = qrCodeList.value?.find { it.rid == item?.rid }
        setFocusItem(focusedItem,type)
    }

    /**
     * --------------------------------------------------------------------------------------------
     */


    private val _isLoaded2 = MutableLiveData<Boolean>(false) // 선택된 아이템 저장
    val isLoaded2: LiveData<Boolean> get() = _isLoaded2

    fun loadStatus(){
        _isLoaded2.value = true
    }


    var vmList = AutoIndexedList<HomeRVItem>()

    // {뷰모델} 아이템 추가 -> DB
    fun addVmItem(item : QRCodeItem){
        if(!isItemExist(item.rid)){
            var newItem = vmList.addAndReturn(item.toHomeRVItem())
            Log.d("checkNewItem","$newItem")
            addHomeRvItem(newItem)
        }
    }
    fun removeItem(item : QRCodeItem){
        val index =  vmList.indexOfFirst { it.rid == item.rid }
        if(index != -1){
            vmList.removeAt(index)
            removeHomeRvItem()
        }
    }
    fun removeItem(index : Int){
//        val item = hItems[index]
//        hItems.removeAt(index)
        val item = vmList[index]
        vmList.removeAt(index)

        removeHomeRvItem()
    }

    // {뷰모델} 아이템 업데이트 -> DB
    fun updateVmItem(){
        updateHomeRvItem()
    }

    private val _homeRVItemList = MediatorLiveData<MutableList<HomeRVItem>>(null)
    val homeRvItemList: LiveData<MutableList<HomeRVItem>> get() = _homeRVItemList

    // [뷰모델] 뷰모델 리스트 - 세팅 함수
    fun setHomeVMList(list : MutableList<HomeRVItem>) {
        vmList.clear()
        vmList.addAll(list)
    }

    // [DB] 아이템 추가 로직
    fun addHomeRvItem(item : HomeRVItem) {
        _homeRVItemList.value = vmList
        //DB 업데이트 - insert(item)
        viewModelScope.launch(Dispatchers.IO) {
            homeRVDao.insertItem(item)
        }
    }
    // [DB] 아이템 삭제
    fun removeHomeRvItem() {
        _homeRVItemList.value = vmList
    }

    // [DB] 아이템 수정 로직
    fun updateHomeRvItem() {
        val iid = vmList
        iid.let { list ->
            val filteredList = list.map { item -> "(position=${item.itemPosition}, title=${item.title})" }
            Log.d("checkHomeRvItemList",filteredList.joinToString(", "))
        }
        _homeRVItemList.value = vmList
        val iid2 = homeRvItemList.value
        iid2.let { list ->
            val filteredList = list!!.map { item -> "{position=${item.itemPosition}, title=${item.title}}" }
            Log.d("checkHomeRvItemList",filteredList.joinToString(", "))
        }


        //DB 업데이트
        viewModelScope.launch(Dispatchers.IO) {
            homeRVDao.deleteAll()
            homeRVDao.insertList(vmList)
            val iid3 = homeRVDao.getAll()
            iid3.let { list ->
                val filteredList = list!!.map { item -> "[position=${item.itemPosition}, title=${item.title}]" }
                Log.d("checkHomeRvItemList",filteredList.joinToString(", "))
            }
        }
    }

    // [DB] DB의 모든 QR 리스트 로드
    suspend fun loadHomeRVList(): Boolean {
        val type2List: MutableList<HomeRVItem>?
        return withContext(Dispatchers.IO) {
            val loadList: List<HomeRVItem> = homeRVDao.getAll()
            val iid = loadList
            iid.let { list ->
                val filteredList = list.map { item -> "[position=${item.itemPosition}, title=${item.title}]" }
                Log.d("checkHomeRvItemList","불러온 리스트 "+ filteredList.joinToString(", "))
            }
            type2List = loadList.map {
                HomeRVItem(
                    rid = it.rid,
                    itemPosition = it.itemPosition,
                    itemType = it.itemType,
                    title = it.title,
                    subTitle = it.subTitle,
                    content = it.content,
                    date = it.date
                )
            }.toMutableList()
            withContext(Dispatchers.Main) {
                _homeRVItemList.value = type2List!!
                setHomeVMList(homeRvItemList.value!!)

            }
            true // 데이터를 성공적으로 로드한 경우 true 반환
        }
    }

    // (도구) : 아이템 존재 여부 확인
    fun isItemExist(rid: Long): Boolean {
        return vmList.any { it.rid == rid }
    }

    // (도구) : 뷰모델 <-> DB 아이템 변환
    fun QRCodeItem.toHomeRVItem(): HomeRVItem {
        return HomeRVItem(
            rid = this.rid,
            itemPosition = null,
            itemType = this.itemType,
            title = this.title,
            subTitle = this.subTitle,
            content = this.content,
            date = this.date
        )
    }
}