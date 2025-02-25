package com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment

import android.content.Context
import android.util.Log
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.constants.fragmentConstants
import com.jkweyu.quickqr.constants.itemFavoritesConstants
import com.jkweyu.quickqr.data.QRCodeItem
import com.jkweyu.quickqr.databinding.FragmentQrDetailBinding
import com.jkweyu.quickqr.view.FrameFragment.FrameFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.QrDetailCodeFragment
import com.jkweyu.quickqr.view.FrameFragment.QrDeatilFragment.QrDetailType.QrDetailContentFragment
import com.jkweyu.quickqr.viewmodel.FrameFragmentViewModel
import com.jkweyu.quickqr.viewmodel.MainViewModel


class QrDetailFragment(private var item : QRCodeItem?): BaseFragment<FragmentQrDetailBinding>(R.layout.fragment_qr_detail) {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var frameFragmentViewModel: FrameFragmentViewModel

    private lateinit var backPressedCallback: OnBackPressedCallback
    private lateinit var  menuItem: MenuItem
    override fun initView() {


        binding.apply {
            menuItem = binding.toolbar.menu.findItem(R.id.star)
            menuItem.isChecked = mainViewModel.focusItem.value!!.favorites != itemFavoritesConstants.FALSE

            setFavoriteIcon()
            // 뷰페이저 기본 설정
            viewpager.adapter = DetailFragmentAdapter(requireActivity(),item)
            viewpager.offscreenPageLimit = 1
            viewpager.setPageTransformer(MarginPageTransformer(20))

            toolbar.navigationIcon?.setTint(ContextCompat.getColor(requireContext(), R.color.white))

            // 툴바 메뉴 아이템 클릭 리스너 설정
            toolbar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.star -> {
                        // 현재 상태의 반대 값으로 체크 상태를 변경
                        Log.d("checkClick","변경전 : ${menuItem.isChecked}")
                        menuItem.isChecked = !menuItem.isChecked
                        Log.d("checkClick","변경후 : ${menuItem.isChecked}\n")
                        val item = mainViewModel.focusItem.value

                        item!!.favorites = if (menuItem.isChecked) itemFavoritesConstants.TRUE else itemFavoritesConstants.FALSE
                        mainViewModel.updateQRCodeItem(item)
                        setFavoriteIcon()

                        true
                    }
                    R.id.delete ->{
                        val item = mainViewModel.focusItem.value
                        mainViewModel.deleteItem(item!!)
                        mainViewModel.changeFragment(fragmentConstants.MAIN)
                        onDetach()
                        true
                    }
//                    R.id.edit -> {
//                        frameFragmentViewModel.changeFragment(fragmentConstantsFrame.QR_CREATE)
//                        mainViewModel.setFocusEditItem(mainViewModel.focusItem.value)
//                        onDetach()
//                        true
//                    }
                    else -> false
                }
            }

            toolbar.setNavigationOnClickListener{
                mainViewModel.changeFragment(fragmentConstants.MAIN)
                onDetach()
            }
        }
    }

    private fun setFavoriteIcon() {
        Log.d("checkFav","${mainViewModel.focusItem.value?.favorites}")
        when(mainViewModel.focusItem.value!!.favorites){
            itemFavoritesConstants.FALSE -> {
                menuItem.setIcon(R.drawable.ic_toolbar_star_unfill)
            }
            else -> {
                menuItem.setIcon(R.drawable.ic_toolbar_star_fill)
            }
        }
    }
    private fun registerOnBackPressedCallback() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                mainViewModel.changeFragment(fragmentConstants.MAIN)
                onDetach()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mainViewModel.setDepth(1)
        val frameFragment = requireActivity()
            .supportFragmentManager
            .findFragmentByTag("frame_fragment_tag") as FrameFragment
        frameFragmentViewModel = ViewModelProvider(frameFragment)[FrameFragmentViewModel::class.java]

        registerOnBackPressedCallback()
        Log.d("onHiddenChanged","QrDetailFragment onAttach backPressedCallback 등록")
    }

    override fun onDetach() {
        super.onDetach()
        if(::backPressedCallback.isInitialized){
            Log.d("onHiddenChanged","QrDetailFragment onDetach backPressedCallback 해제")
            backPressedCallback.remove()
        }
    }
}
class DetailFragmentAdapter(activity: FragmentActivity,item : QRCodeItem?): FragmentStateAdapter(activity) {
    val fragments: List<Fragment>

    init {
        fragments = listOf(QrDetailContentFragment(item), QrDetailCodeFragment(item))

    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}