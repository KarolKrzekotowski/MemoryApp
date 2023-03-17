package com.example.memoryapp.ui.fragment.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.memoryapp.databinding.FragmentLeaderboardBinding

import com.example.memoryapp.ui.fragment.leaderboard.adapter.LeaderboardPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class LeaderboardFragment : Fragment() {

    private var _binding : FragmentLeaderboardBinding?= null
    private val binding get() = _binding!!

    private lateinit var viewPager2: ViewPager2
    private lateinit var leaderboardPagerAdapter: LeaderboardPagerAdapter
    private val tabsNames = arrayOf("ranking lokalny","ranking globalny")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLeaderboardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        leaderboardPagerAdapter = LeaderboardPagerAdapter(this)
        viewPager2 = binding.pagerRank
        viewPager2.adapter = leaderboardPagerAdapter

        val tablayout = binding.tabLayout
        TabLayoutMediator(tablayout,viewPager2){ tab, position ->
            tab.text = tabsNames[position]
        }.attach()
        binding.backRanks.setOnClickListener {
            val action = LeaderboardFragmentDirections.actionLeaderboardFragmentToMainMenuFragment(false)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}