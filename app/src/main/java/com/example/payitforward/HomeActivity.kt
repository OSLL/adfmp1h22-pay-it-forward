package com.example.payitforward

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.payitforward.adapters.TabsPagerAdapter
import com.example.payitforward.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding

    private fun openFilterActivity(view: View?) {
        val intent = Intent(view!!.context, FilterActivity::class.java)
        startActivity(intent)
    }

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

        mBinding.filterButtonHome.setOnClickListener {
            // TODO: Fix it
            Toast.makeText(this, "You clicked on item", Toast.LENGTH_LONG).show()
                //view -> openFilterActivity(view)
        }

        
    }
}