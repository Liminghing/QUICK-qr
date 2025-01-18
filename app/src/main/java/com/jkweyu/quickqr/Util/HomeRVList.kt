package com.jkweyu.quickqr.Util

import android.util.Log
import com.jkweyu.quickqr.viewmodel.home.HomeItem
import java.util.Collections


class HomeRVList(
    private val type1: Any,
    private val type2: Any,
    private val type3: Any,
    private val mylist: MutableList<HomeItem>,
    private val type4: Any,
    private val type5: Any
) : AbstractList<Any>() {

    override val size: Int
        get() = 5 + mylist.size // type1 + type2 + type3 + mylist + type4 + type5

    override fun get(index: Int): Any {
        Log.d("HomeRVList","Index: $index, Size: $size")
        return when (index) {
            0 -> type1 // type1 객체
            1 -> type2 // type2 객체
            2 -> type3 // type3 객체
            in 3..mylist.size + 2 -> mylist[index - 3] // mylist의 동적 내용
            mylist.size + 3 -> type4 // type3 객체
            mylist.size + 4 -> type5 // type4 객체
            else -> throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
    }
    fun getID(position : Int) : Any {
        Log.d("HomeRVList","position: $position, Size: $size")
        return when (position) {
            0 -> type1 // type1 객체
            1 -> type2 // type2 객체
            2 -> type3 // type3 객체
            in 3..mylist.size + 2-> mylist[position - 3].itemID// mylist의 동적 내용
            mylist.size + 3 -> type4 // type3 객체
            mylist.size + 4 -> type5 // type4 객체
            else -> throw IndexOutOfBoundsException("position: $position, Size: $size")
        }
    }
    fun removeAt(position : Int){
        mylist.removeAt(position-3)
    }
    fun addItem(item : HomeItem){
        mylist.add(item)
    }
    fun swapList(i : Int, j : Int){
        Collections.swap(mylist, i, j)
    }
    fun clear() {
        mylist.clear()
    }
    fun addAll(newList: MutableList<HomeItem>) {
        mylist.addAll(newList)
    }
}
