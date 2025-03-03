//package com.jkweyu.quickqr.view.MainFragment.all
//
//import android.content.Context
//import android.widget.ArrayAdapter
//import com.jkweyu.quickqr.R
//import com.jkweyu.quickqr.base.BaseFragment
//import com.jkweyu.quickqr.databinding.FragmentLanguageLayoutBinding
//import com.jkweyu.quickqr.util.LocaleHelper
//
//class LanguageFragment: BaseFragment<FragmentLanguageLayoutBinding>(
//    R.layout.fragment_language_layout) {
//    override fun initView() {
//        binding.apply {
//
//            // 표시할 리스트 데이터
//            val items = listOf("English","한국어","Español","Français","Português","Deutsch","Tiếng Việt","Italiano")
//
//            // 기본 제공되는 ArrayAdapter 사용
//            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
//            languageListView.adapter = adapter
//            // 리스트 항목 클릭 이벤트 처리
//            languageListView.setOnItemClickListener { _, _, position, _ ->
//                when(items[position]){
//                    "English" -> {
//                        changeLanguage("en")
//                        // 세팅 저장
//                    }
//                    "한국어" -> {
//                        changeLanguage("ko")
//
//                    }
//                    "Español" -> {
//
//                    }
//                    "Français" -> {
//
//                    }
//                    "Português" -> {
//
//                    }
//                    "Deutsch" -> {
//
//                    }
//                    "Tiếng Việt" -> {
//
//                    }
//                    "Italiano" -> {
//
//                    }
//
//                }
//
//            }
//
//        }
//    }
//    private fun changeLanguage(language: String) {
//        // SharedPreferences를 사용하여 선택한 언어 저장
//        val prefs = requireActivity().getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
//        prefs.edit().putString("selected_language", language).apply()
//
//        // 로케일 변경 적용
//        LocaleHelper.setLocale(requireContext(), language)
//
//        // 액티비티를 올바르게 재생성
//        requireActivity().recreate()
//
//        // Intent와 finish() 방식은 문제를 일으킬 수 있으므로 사용하지 않음
//        // recreate() 메서드가 새 로케일로 UI를 새로 고치는데 더 깔끔함
//    }
//
//
//}
