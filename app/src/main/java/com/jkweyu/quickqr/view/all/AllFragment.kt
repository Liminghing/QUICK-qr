package com.jkweyu.quickqr.view.all

import android.content.Intent
import androidx.lifecycle.Observer
import com.jkweyu.quickqr.DetailActivity
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentAllBinding
import com.jkweyu.quickqr.viewmodel.all.AllItemViewModel


class AllFragment: BaseFragment<FragmentAllBinding>(R.layout.fragment_all) {
    private lateinit var allFragmentViewModel: AllItemViewModel
    override fun initView() {
        binding.apply {
            allFragmentViewModel = AllItemViewModel()
            allViewModel = allFragmentViewModel

            allFragmentViewModel.selectedItem.observe(this@AllFragment, Observer {
                if (it != null){
                    val intent = Intent(this@AllFragment.context, DetailActivity::class.java)
                    intent.putExtra("type",it)
                    startActivity(intent)
                }
            })
        }

    }
}