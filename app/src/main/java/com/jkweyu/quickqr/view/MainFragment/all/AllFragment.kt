package com.jkweyu.quickqr.view.MainFragment.all

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.FragmentAllBinding
import com.jkweyu.quickqr.viewmodel.MainViewModel
import com.jkweyu.quickqr.viewmodel.all.AllItemViewModel


class AllFragment: BaseFragment<FragmentAllBinding>(R.layout.fragment_all) {
    private lateinit var allFragmentViewModel: AllItemViewModel
    private lateinit var mainViewModel: MainViewModel
    override fun initView() {
        binding.apply {
            allFragmentViewModel = AllItemViewModel()
            mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
            mainActViewModel = mainViewModel

            mainViewModel.allFragSelectedItem.observe(this@AllFragment, Observer {
                if (it != null){
                    mainViewModel.changeFragment(fragmentConstants.TITLE_FRAME)
//                    val intent = Intent(this@AllFragment.context, TitleFrameActivity::class.java)
//                    intent.putExtra("type",it)
//                    startActivity(intent)
                }
            })
        }

    }
}