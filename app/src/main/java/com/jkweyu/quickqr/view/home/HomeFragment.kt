package com.jkweyu.quickqr.view.home

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentHomeBinding
import com.jkweyu.quickqr.viewmodel.HomeItem
import com.jkweyu.quickqr.viewmodel.HomeRVItemViewModel


class HomeFragment: BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var viewModel: HomeRVItemViewModel
    private lateinit var adapter: HomeRvAdapter
    var touchHelper: ItemTouchHelper? = null
    override fun initView() {
        binding.apply {
            var myList = mutableListOf(
                HomeItem(1, "1"),
                HomeItem(1, "2"),
                HomeItem(2, "3"),
                HomeItem(2, "4"),
                HomeItem(2, "5"),
                HomeItem(3, "6"),
                HomeItem(2, "7"),
                HomeItem(2, "8"),
                HomeItem(2, "9"),
                HomeItem(3, "10"),
                HomeItem(2, "11"),
                HomeItem(2, "12"),
                HomeItem(2, "13"),
                HomeItem(3, "14"),
                HomeItem(null, null)
            )

            //뷰모델
            viewModel = ViewModelProvider(this@HomeFragment).get(HomeRVItemViewModel::class.java)
            //binding.lifecycleOwner = this@HomeFragment
            homeRecyclerview.layoutManager = GridLayoutManager(requireContext(),3)
            //어댑터
            adapter =  HomeRvAdapter(myList,viewModel,this@HomeFragment)
            //수정모드 옵저빙
            viewModel.itemVisibility.observe(this@HomeFragment, Observer { isVisible ->
                Log.d("ItemMoveCallback","observed : $isVisible in HomeFragment")
                if(isVisible) {
                    binding.bottomView.isVisible = true
                }else{
                    binding.bottomView.isVisible = false
                }
            })

            val callback: ItemTouchHelper.Callback = ItemMoveCallback(adapter,viewModel)
            touchHelper = ItemTouchHelper(callback)
            touchHelper!!.attachToRecyclerView(homeRecyclerview)

            myItem = viewModel
            homeRecyclerview.adapter = adapter
        }
    }
}
