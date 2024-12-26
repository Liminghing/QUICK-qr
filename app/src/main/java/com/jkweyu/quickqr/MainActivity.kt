package com.jkweyu.quickqr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        // 기본 화면 설정
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.navigation_history -> {
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.navigation_payment -> {
                    loadFragment(PaymentFragment())
                    true
                }
                R.id.navigation_favorites -> {
                    loadFragment(FavoritesFragment())
                    true
                }
                R.id.navigation_all -> {
                    loadFragment(AllFragment())
                    true
                }
                else -> false
            }
        }

    }
    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        return true
    }
}