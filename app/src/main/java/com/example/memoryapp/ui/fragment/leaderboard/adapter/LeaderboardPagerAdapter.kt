package com.example.memoryapp.ui.fragment.leaderboard.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.memoryapp.ui.fragment.leaderboard.global.GlobalRankFragment
import com.example.memoryapp.ui.fragment.leaderboard.local.LocalRankFragment

class LeaderboardPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LocalRankFragment()
            1 -> GlobalRankFragment()
            else -> createFragment(position)
        }
    }

}