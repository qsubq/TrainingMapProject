package com.example.googlemap.view.screen.containerFragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.googlemap.view.screen.main.MainFragment
import com.example.googlemap.view.screen.viewPagerScreen.ViewPagerFragment

class MainAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) ViewPagerFragment()
        else {MainFragment()}
    }
}