package com.jkweyu.quickqr.view.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.room.Room
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.data.homervdata.HomeRVItem
import com.jkweyu.quickqr.data.homervdata.HomeRVItemDatabase
import com.jkweyu.quickqr.databinding.FragmentHomeBinding
import com.jkweyu.quickqr.viewmodel.HomeItem
import com.jkweyu.quickqr.viewmodel.HomeRVItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    companion object {
        val VIEW_TYPE_MAIN = 0
        val VIEW_TYPE_MENU = 1
        val VIEW_TYPE_ADD_MENU = 2
        val VIEW_TYPE_EMPTY = 3
    }
    private lateinit var viewModel: HomeRVItemViewModel
    private lateinit var homeAdapter: HomeMultiRVAdapter
    var touchHelper: ItemTouchHelper? = null
    private var rvList = mutableListOf<HomeItem>()
    override fun initView() {
        binding.apply {
            //뷰모델 생성
            viewModel = ViewModelProvider(this@HomeFragment).get(HomeRVItemViewModel::class.java)
            //데이터 바인딩
            myItem = viewModel
            Log.d("myLifeCycle","HomeFragment")



            homeAdapter = HomeMultiRVAdapter(rvList,viewModel,this@HomeFragment)

            val callback: ItemTouchHelper.Callback = ItemMoveCallback(homeAdapter,viewModel)
            touchHelper = ItemTouchHelper(callback)
            touchHelper!!.attachToRecyclerView(homeRecyclerview)

            homeRecyclerview.adapter = homeAdapter

            val gridLayoutManager = GridLayoutManager(requireContext(), 3)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = homeAdapter.getItemViewType(position)
                    Log.d("SpanSize", "Item at position $position has view type: $viewType")
                    return if (viewType == VIEW_TYPE_MENU || viewType == VIEW_TYPE_ADD_MENU) 1 else 3
                }
            }
            homeRecyclerview.layoutManager = gridLayoutManager

            binding.button3.setOnClickListener {
                viewModel.toggleItemVisibilityOff()
                rvList.add(HomeItem(itemType = VIEW_TYPE_MENU, menuType = 1, title = "test"))
                homeAdapter.notifyDataSetChanged()


            }



            viewModel.itemVisibility.observe(this@HomeFragment, Observer { isVisible ->
                Log.d("ItemMoveCallback","observed : $isVisible in HomeFragment")
                if(isVisible) {
                    binding.bottomView.isVisible = true
                }else{
                    binding.bottomView.isVisible = false
                }
            })
        }
    }
    private fun loadList() :  MutableList<HomeItem> {
        val db = Room.databaseBuilder(
            requireContext(),
            HomeRVItemDatabase::class.java, "database-name"
        ).build()
        val homeRVDao = db.homeRVItemDao()
        var loadList = mutableListOf<HomeItem>()
        lifecycleScope.launch {
            val users: List<HomeRVItem> = withContext(Dispatchers.IO) {
                homeRVDao.getAll() // 백그라운드 스레드에서 실행
            }
            loadList = users.map { homeRVItem ->
                // HomeRVItem을 HomeItem으로 변환하는 로직
                HomeItem(
                    // 예시: HomeRVItem의 프로퍼티를 사용해 HomeItem을 생성
                    itemID = homeRVItem.itemID,
                    itemType = homeRVItem.itemType,
                    menuType = homeRVItem.menuType,
                    title = homeRVItem.title
                )
            }.toMutableList()

        }
        return loadList
    }
}