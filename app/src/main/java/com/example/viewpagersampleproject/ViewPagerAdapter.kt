package com.example.viewpagersampleproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
//        return NUM_PAGES

        // For endless viewpager
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
//        return if (position == 0) FirstFragment() else if (position == 1) SecondFragment() else ThirdFragment()

        // For endless viewpager
        return if (position % NUM_PAGES == 0) FirstFragment() else if (position % NUM_PAGES == 1) SecondFragment() else ThirdFragment()

    }

    companion object {
        private const val NUM_PAGES = 3
    }
}