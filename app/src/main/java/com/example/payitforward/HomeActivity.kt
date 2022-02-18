package com.example.payitforward

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.payitforward.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        mBinding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.pink))
        mBinding.tabLayout.tabTextColors =
            ContextCompat.getColorStateList(this, android.R.color.white)
        val numberOfTabs = 3
        mBinding.tabLayout.tabMode = TabLayout.MODE_FIXED
        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
        mBinding.tabsViewpager.adapter = adapter
        TabLayoutMediator(mBinding.tabLayout, mBinding.tabsViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "All"
                }
                1 -> {
                    tab.text = "Get"
                }
                2 -> {
                    tab.text = "Give"
                }
            }
        }.attach()

    }
}