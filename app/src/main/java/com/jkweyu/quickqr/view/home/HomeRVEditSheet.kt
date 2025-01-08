package com.jkweyu.quickqr.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jkweyu.quickqr.databinding.DialogHomeRvEditSheetBinding

class HomeRVEditSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogHomeRvEditSheetBinding.inflate(inflater, container, false)

        // 바텀 시트의 버튼 클릭 등의 동작을 처리할 수 있음
        binding.buttonClose.setOnClickListener {
            dismiss() // 바텀 시트 닫기
        }
        return binding.root
    }
}
