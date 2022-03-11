package com.example.payitforward.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.payitforward.R
import com.example.payitforward.adapters.TabsPagerAdapter
import com.example.payitforward.databinding.ActivityHomeBinding
import com.example.payitforward.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_home)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
//        setContentView(mBinding.root)
        val root: View = binding.root
        binding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        context?.let { ContextCompat.getColor(it, R.color.pink) }
            ?.let { binding.tabLayout.setBackgroundColor(it) }
        binding.tabLayout.tabTextColors =
            context?.let { ContextCompat.getColorStateList(it, android.R.color.white) }
        val numberOfTabs = 3
        binding.tabLayout.tabMode = TabLayout.MODE_FIXED
        val adapter = TabsPagerAdapter(getParentFragmentManager(), lifecycle, numberOfTabs)
        binding.tabsViewpager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.tabsViewpager) { tab, position ->
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
        return root
    }
}