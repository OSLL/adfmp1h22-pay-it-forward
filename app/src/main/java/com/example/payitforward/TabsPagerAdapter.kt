package com.example.payitforward

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) :
    FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "All Fragment")
                val allFragment = FragmentHomeAll()
                allFragment.arguments = bundle
                return allFragment
            }
            1 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Get Fragment")
                val getFragment = FragmentHomeGet()
                getFragment.arguments = bundle
                return getFragment
            }
            2 -> {
                val bundle = Bundle()
                bundle.putString("fragmentName", "Give Fragment")
                val giveFragment = FragmentHomeGive()
                giveFragment.arguments = bundle
                return giveFragment
            }
            else -> return FragmentHomeAll()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}