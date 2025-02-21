package com.jkweyu.quickqr.view.MainFragment.history

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jkweyu.quickqr.Util.DateTextUtil.toItemString
import com.jkweyu.quickqr.Util.DateTextUtil.toSimpleString
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.viewmodel.MainViewModel


object HistoryRVBindingAdapter {
    @BindingAdapter("rvViewModel","rvItemType")
    @JvmStatic
    fun setItem(recyclerView: RecyclerView,
                viewModel: MainViewModel,
                type: Int
    ){

        if(recyclerView.adapter == null){
            val list = viewModel.getQRCodeList()
            Log.d("dfgjkl","HistoryRVBindingAdapter ${list}")
//            val myList = insertItemOnDateChange(setListType(type,list),type)

            //
            val myList = setListType(type,list)

            val historyAdapter = HistoryMultiRVAdapter(sortListByDateDescending(myList),viewModel)
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.adapter = historyAdapter
            recyclerView.addItemDecoration(DateDividerDecoration(recyclerView.context,sortListByDateDescending(myList)))
        }
    }

    fun sortListByDateDescending(myList: MutableList<QRCodeItem>): MutableList<QRCodeItem> {
        return myList.sortedByDescending { it.date }.toMutableList()
    }

    fun setListType(type: Int,items : MutableList<QRCodeItem>):MutableList<QRCodeItem>{
        var list = items
        var menuType0List : MutableList<QRCodeItem> = items
        if(type != 0){
            menuType0List = list.filter { it.itemType == type }.toMutableList()
        }
        return menuType0List
    }
}
class DateDividerDecoration(
    private val context: Context,
    private val itemList: MutableList<QRCodeItem> // Item은 날짜를 포함한 데이터 클래스
) : RecyclerView.ItemDecoration() {



    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        typeface = Typeface.DEFAULT_BOLD
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val idex = parent.getChildAdapterPosition(view)
        if(idex == 0){
            outRect.set(0,80,0,0)
        }else{
            val currentItem = itemList[idex]
            val previousItem = itemList[idex - 1]
            if (currentItem.date.toSimpleString() != previousItem.date.toSimpleString()) {
                outRect.set(0,60,0,0)
            }else{
                outRect.set(0,0,0,0)
            }
        }
    }
    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        for (i in 0 until itemList.size) {
            if(i == 0){
                val view = parent.findViewHolderForAdapterPosition(i)?.itemView ?: continue
                val top = view.top.toFloat()

                // 날짜 텍스트 표시
                c.drawText(itemList[i].date.toItemString(), view.left.toFloat() + 20, top - 30, textPaint)
            }else{
                val currentItem = itemList[i]
                val previousItem = itemList[i - 1]
                if (currentItem.date.toSimpleString() != previousItem.date.toSimpleString()) { // 날짜가 다르면 구분선 추가
                    val view = parent.findViewHolderForAdapterPosition(i)?.itemView ?: continue
                    val top = view.top.toFloat()

                    // 날짜 텍스트 표시
                    c.drawText(currentItem.date.toItemString(), view.left.toFloat() + 20, top - 30, textPaint)
                }
            }
        }
    }

}

