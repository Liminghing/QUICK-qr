package com.jkweyu.quickqr.view.home.qrcreate

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.MarginPageTransformer
import com.jkweyu.quickqr.R
import com.jkweyu.quickqr.base.BaseFragment
import com.jkweyu.quickqr.databinding.FragmentQrChoiceBinding
import com.jkweyu.quickqr.view.home.qrcreate.holder.CardQrFragment

class QRChoiceFragment(): BaseFragment<FragmentQrChoiceBinding>(R.layout.fragment_qr_choice) {
    override fun initView() {
        binding.apply {
            //데이터 바인딩
            viewpager.adapter = MyFragmentPagerAdapter(requireActivity())
            // 옆 페이지도 보이도록 설정
            viewpager.offscreenPageLimit = 1
            viewpager.setPageTransformer(MarginPageTransformer(20))

        }
    }
}
class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    val fragments: List<Fragment>

    init {
        //fragments = listOf(CardQrFragment(), GeneralQrFragment(), ItemQrFragment())
        fragments = listOf(CardQrFragment())
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}