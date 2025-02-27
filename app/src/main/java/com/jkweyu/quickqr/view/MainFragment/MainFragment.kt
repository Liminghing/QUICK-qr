package com.jkweyu.quickqr.view.MainFragment

import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentMainBinding
import com.jkweyu.quickqr.view.MainFragment.all.AllFragment
import com.jkweyu.quickqr.view.MainFragment.favorites.FavoritesFragment
import com.jkweyu.quickqr.view.MainFragment.history.HistoryFragment
import com.jkweyu.quickqr.view.MainFragment.home.HomeFragment
import com.jkweyu.quickqr.viewmodel.MainViewModel


class MainFragment: BaseFragment<FragmentMainBinding>(R.layout.fragment_main) {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var homeFragment : HomeFragment
    private lateinit var historyFragment : HistoryFragment
    private lateinit var favoritesFragment : FavoritesFragment
    private lateinit var allFragment : AllFragment

    private lateinit var backPressedCallback: OnBackPressedCallback
    private var backPressedTime: Long = 0

    override fun initView() {


        binding.apply {
            button2.setOnClickListener {
                val iid = mainViewModel.vmList
                iid?.let { list ->
                    val filteredList = list.map { item -> "[position=${item.itemPosition}, title=${item.title}]" }
                    Log.d("checkHomeRvItemList", filteredList.joinToString(", "))
                } ?: Log.d("checkHomeRvItemList", "List is null")
            }

            homeFragment = HomeFragment()
            historyFragment = HistoryFragment()
            favoritesFragment = FavoritesFragment()
            allFragment = AllFragment()

            childFragmentManager.beginTransaction()
                .add(R.id.container, homeFragment,"HomeFragment").hide(homeFragment)
                .add(R.id.container, historyFragment,"HistoryFragment").hide(historyFragment)
                .add(R.id.container, favoritesFragment,"FavoritesFragment").hide(favoritesFragment)
                .add(R.id.container, allFragment,"AllFragment").hide(allFragment)
                .commit()

            childFragmentManager.beginTransaction()
                .show(homeFragment)
                .hide(historyFragment)
                .hide(favoritesFragment)
                .hide(allFragment)
                .commit()

            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
//                        loadFragment(HomeFragment())
                        childFragmentManager.beginTransaction()
                            .show(homeFragment)
                            .hide(historyFragment)
                            .hide(favoritesFragment)
                            .hide(allFragment)
                            .commit()
                        true
                    }
                    R.id.navigation_history -> {
//                        loadFragment(HistoryFragment())
                        childFragmentManager.beginTransaction()
                            .show(historyFragment)
                            .hide(favoritesFragment)
                            .hide(allFragment)
                            .hide(homeFragment)
                            .commit()
                        true
                    }
//                    R.id.navigation_payment -> {
//                        loadFragment(PaymentFragment())
//                        true
//                    }
                    R.id.navigation_favorites -> {
//                        loadFragment(FavoritesFragment())
                        childFragmentManager.beginTransaction()
                            .show(favoritesFragment)
                            .hide(allFragment)
                            .hide(homeFragment)
                            .hide(historyFragment)
                            .commit()
                        true
                    }
                    R.id.navigation_all -> {
//                        loadFragment(AllFragment())
                        childFragmentManager.beginTransaction()
                            .show(allFragment)
                            .hide(homeFragment)
                            .hide(historyFragment)
                            .hide(favoritesFragment)
                            .commit()
                        true
                    }
                    else -> false
                }
            }
        }
    }
    fun loadFragment(fragment: Fragment): Boolean {
        // 현재 선택된 메뉴 아이템 ID 저장
        val currentFragmentId = when (fragment) {
            is HomeFragment -> R.id.navigation_home
            is HistoryFragment -> R.id.navigation_history
            is FavoritesFragment -> R.id.navigation_favorites
            is AllFragment -> R.id.navigation_all
            else -> null
        }

        // Fragment 전환 시 백스택에 저장
        childFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(currentFragmentId?.toString())
            .commit()

        return true
    }
    fun returnToMainFragment(selectedFragmentId: Int) {
        binding.bottomNavigationView.selectedItemId = selectedFragmentId
    }
    private fun registerOnBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - backPressedTime < 2000) {
                    requireActivity().finish() // 앱 종료
                } else {
                    backPressedTime = System.currentTimeMillis()
                    Toast.makeText(requireActivity(), "뒤로 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        if (!hidden) {
            Log.d("dfgjkl","MainFragment ${mainViewModel.qrCodeList.value} ")
            Log.d("onHiddenChanged","Main backPressedCallback 등록")
            mainViewModel.setDepth(0)
//            mainViewModel.setFocusItem(null)
            registerOnBackPressedCallback()
        }else{
            if(::backPressedCallback.isInitialized){
                Log.d("onHiddenChanged","Main backPressedCallback 해제")
                backPressedCallback.remove()
            }

        }
    }
}