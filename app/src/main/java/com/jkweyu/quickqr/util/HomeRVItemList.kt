package com.jkweyu.quickqr.util

import com.jkweyu.quickqr.data.homervdata.HomeRVItem

import java.util.Date

data class HomeRVItem(
    val rid: Long,
    var itemPosition: Int?,
    val itemType: Int,
    val title: String,
    val subTitle: String,
    val content: String,
    val date: Date
)

class AutoIndexedList<T : HomeRVItem> : ArrayList<T>() {

    /**
     * 새 아이템 추가시 자동으로 현재 리스트 크기를 인덱스로 지정하고 추가된 아이템 반환
     * @return 추가된 아이템
     */
    override fun add(element: T): Boolean {
        element.itemPosition = size
        val result = super.add(element)
        return result
    }

    /**
     * 새 아이템 추가 후 추가된 아이템 자체를 반환하는 확장 메서드
     * @return 추가된 아이템
     */
    fun addAndReturn(element: T): T {
        element.itemPosition = size
        super.add(element)
        return element
    }

    /**
     * 특정 인덱스에 아이템 추가시 해당 인덱스를 itemPosition으로 지정
     * 그리고 기존 요소들의 인덱스 업데이트
     */
    override fun add(index: Int, element: T) {
        element.itemPosition = index
        super.add(index, element)
        updateIndices(index + 1)
    }

    /**
     * 특정 인덱스에 아이템 추가 후 추가된 아이템 자체를 반환하는 확장 메서드
     * @return 추가된 아이템
     */
    fun addAndReturn(index: Int, element: T): T {
        element.itemPosition = index
        super.add(index, element)
        updateIndices(index + 1)
        return element
    }

    /**
     * 컬렉션 추가시 각 요소의 itemPosition 업데이트
     */
    override fun addAll(elements: Collection<T>): Boolean {
        val startIndex = size
        val result = super.addAll(elements)
        if (result) {
            for (i in startIndex until size) {
                this[i].itemPosition = i
            }
        }
        return result
    }

    /**
     * 특정 인덱스에 컬렉션 추가시 각 요소의 itemPosition 업데이트
     */
    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        val result = super.addAll(index, elements)
        if (result) {
            updateIndices(index)
        }
        return result
    }

    /**
     * 아이템 제거시 인덱스 업데이트
     */
    override fun remove(element: T): Boolean {
        val index = indexOf(element)
        val result = super.remove(element)
        if (result && index >= 0) {
            updateIndices(index)
        }
        return result
    }

    /**
     * 특정 인덱스 아이템 제거시 인덱스 업데이트
     */
    override fun removeAt(index: Int): T {
        val removed = super.removeAt(index)
        updateIndices(index)
        return removed
    }

    /**
     * 두 요소 위치 교환시 itemPosition 업데이트
     */
    fun swap(fromPosition: Int, toPosition: Int) {
        if (fromPosition < 0 || fromPosition >= size || toPosition < 0 || toPosition >= size) {
            return
        }

        val temp = this[fromPosition]
        this[fromPosition] = this[toPosition]
        this[toPosition] = temp

        // swap된 위치의 인덱스가 itemPosition에 반영
        this[fromPosition].itemPosition = fromPosition
        this[toPosition].itemPosition = toPosition
    }

    /**
     * 전체 리스트 아이템의 itemPosition 갱신
     */
    fun refreshIndices() {
        updateIndices(0)
    }

    /**
     * 시작 인덱스부터 모든 요소의 인덱스 업데이트
     */
    private fun updateIndices(startIndex: Int) {
        for (i in startIndex until size) {
            this[i].itemPosition = i
        }
    }

    /**
     * 리스트 초기화시 인덱스도 초기화
     */
    override fun clear() {
        super.clear()
    }
}
