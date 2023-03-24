package com.example.memoryapp.ui.fragment.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentLeaderboardBinding
import com.example.memoryapp.game.hideUI

import com.example.memoryapp.ui.fragment.leaderboard.adapter.LeaderboardPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class LeaderboardFragment : Fragment() {

    private var _binding : FragmentLeaderboardBinding?= null
    private val binding get() = _binding!!

    private val viewmodel by activityViewModels<LeaderboardViewmodel>()
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
        viewmodel.loadLocalData()
        viewmodel.loadRemoteData()
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
        viewmodel.loadData()
        viewmodel.filterSize(16)
        binding.filtringButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_size_16 -> {
                        viewmodel.filterSize(16)
                        true
                    }
                    R.id.action_size_24 -> {
                        viewmodel.filterSize(24)
                        true
                    }
                    R.id.action_size_32 -> {
                        viewmodel.filterSize(32)
                        true
                    }
                    R.id.result -> {
                        viewmodel.filterReference(true)
                        true
                    }
                    R.id.tries -> {
                        viewmodel.filterReference(false)
                        true
                    }
                     else -> false
                }
            }
            popupMenu.inflate(R.menu.filtring_options)
            popupMenu.show()
        }
        view.viewTreeObserver?.addOnWindowFocusChangeListener { hasFocus ->
            hideUI()/*do your stuff here*/ }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}