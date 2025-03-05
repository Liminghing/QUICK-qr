package com.jkweyu.quickqr.view

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseActivity
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.databinding.ActivityMainBinding
import com.jkweyu.quickqr.view.FrameFragment.FrameFragment
import com.jkweyu.quickqr.view.MainFragment.MainFragment
import com.jkweyu.quickqr.view.TitleFrameFragment.TitleFrameFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel

class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var mainViewModel: MainViewModel
//
    private lateinit var mainFragment : MainFragment
    private lateinit var frameFragment : FrameFragment
    private lateinit var titleFrameFragment : TitleFrameFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MainViewModel::class.java]
        

        mainFragment = MainFragment()
        frameFragment = FrameFragment()
        titleFrameFragment = TitleFrameFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.container, mainFragment,"main_fragment_tag").hide(mainFragment)
            .add(R.id.container, frameFragment, "frame_fragment_tag").hide(frameFragment)
            .add(R.id.container, titleFrameFragment,"title_frame_fragment_tag").hide(titleFrameFragment)
            .commit()
        binding.apply {
            mainViewModel.activityFragment.observe(this@MainActivity) { fragment ->
                when (fragment) {
                    fragmentConstants.MAIN -> {
                        supportFragmentManager.beginTransaction()
                            .show(mainFragment)
                            .hide(frameFragment)
                            .hide(titleFrameFragment)
                            .commit()
//                                loadMainFragment()
                    }
                    fragmentConstants.FRAME -> {
                        supportFragmentManager.beginTransaction()
                            .show(frameFragment)
                            .hide(mainFragment)
                            .hide(titleFrameFragment)
                            .commit()
//                                loadFrameFragment()
                    }
                    fragmentConstants.TITLE_FRAME -> {
                        supportFragmentManager.beginTransaction()
                            .show(titleFrameFragment)
                            .hide(mainFragment)
                            .hide(frameFragment)
                            .commit()
//                                loadTitleFrameFragment()
                    }
                }
            }
        }
    }
    private fun loadMainFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment(), "main_fragment_tag")
            .commit()
    }
    private fun loadFrameFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, FrameFragment(), "frame_fragment_tag")
            .commit()
    }
    private fun loadTitleFrameFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, TitleFrameFragment(), "title_frame_fragment_tag")
            .commit()
    }
}