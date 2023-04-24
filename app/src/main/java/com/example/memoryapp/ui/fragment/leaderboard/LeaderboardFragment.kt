package com.example.memoryapp.ui.fragment.leaderboard

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentLeaderboardBinding

import com.example.memoryapp.game.hideUI
import com.example.memoryapp.ui.activities.MainActivity

import com.example.memoryapp.ui.fragment.leaderboard.adapter.LeaderboardPagerAdapter
import com.example.memoryapp.utils.textGradient
import com.example.memoryapp.utils.translate
import com.google.android.material.tabs.TabLayoutMediator


class LeaderboardFragment : Fragment() {

    private var _binding : FragmentLeaderboardBinding?= null
    private val binding get() = _binding!!

    private val viewmodel by activityViewModels<LeaderboardViewmodel>()
    private lateinit var viewPager2: ViewPager2
    private lateinit var leaderboardPagerAdapter: LeaderboardPagerAdapter
    private var tabsNames = Array<String>(8){"it = $it"}
    private val mediaPlayer = MainActivity.mediaPlayer

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
        tabsNames = resources.getStringArray(R.array.tabNames)
        viewmodel.loadLocalData()
        viewmodel.loadRemoteData()
        leaderboardPagerAdapter = LeaderboardPagerAdapter(this)
        viewPager2 = binding.pagerRank
        viewPager2.adapter = leaderboardPagerAdapter

        val tablayout = binding.tabLayout
        val color = requireContext().getColor(R.color.red_gradient)
        val color2 = requireContext().getColor(R.color.green_gradient)
        val colorArray = intArrayOf(color,color2)
        tablayout.setTabTextColors(color,color2)

//        tabLayout.setTabTextColors(getResources().getColor(R.color.blue_200), getResources().getColor(R.color.white));

        TabLayoutMediator(tablayout,viewPager2){ tab, position ->

            tab.text = tabsNames[position]
        }.attach()

        binding.goBack.setOnClickListener {
            val action = LeaderboardFragmentDirections.actionLeaderboardFragmentToMainMenuFragment(false)
            findNavController().navigate(action)
        }
        binding.langrank.setOnClickListener {
            translate()
            recreate()
        }
        hideUI()
        binding.langrank.textGradient()
        if (mediaPlayer.isPlaying){
            binding.music.setImageResource(R.drawable.sound_on)
        }
        else{
            binding.music.setImageResource(R.drawable.sound_off)
        }
        binding.music.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                binding.music.setImageResource(R.drawable.sound_off)
            }
            else{
                mediaPlayer.start()
                binding.music.setImageResource(R.drawable.sound_on)
            }
        }
        viewmodel.loadData()
        viewmodel.filterSize(16)

        binding.apply {

            filter16.setOnClickListener {
                viewmodel.filterSize(16)
                fixSizeColor(filter16)

            }
            filter20.setOnClickListener {
                viewmodel.filterSize(20)
                fixSizeColor(filter20)

            }
            filter24.setOnClickListener {
                viewmodel.filterSize(24)
                fixSizeColor(filter24)

            }
            filterTime.setOnClickListener {
                viewmodel.filterReference(true)
                fixResultColors()
            }
            filterTries.setOnClickListener {
                viewmodel.filterReference(false)
                fixResultColors()
            }
        }
        fixResultColors()
        fixSizeColor(binding.filter16)

    }
    private fun fixResultColors(){

        if (!viewmodel.time){
            binding.filterTime.background = resources.getDrawable(R.drawable.rounded_empty_green)
            binding.filterTime.setTextColor(resources.getColor(R.color.white))
            binding.filterTries.background = resources.getDrawable(R.drawable.rounded_green)
            binding.filterTries.setTextColor(resources.getColor(R.color.black))
        }
        else{
            binding.filterTime.background = resources.getDrawable(R.drawable.rounded_green)
            binding.filterTime.setTextColor(resources.getColor(R.color.black))
            binding.filterTries.background = resources.getDrawable(R.drawable.rounded_empty_green)
            binding.filterTries.setTextColor(resources.getColor(R.color.white))
        }
    }
    private fun fixSizeColor(button: TextView){
        val text:List<TextView>
        text = listOf(binding.filter16,binding.filter20,binding.filter24)
        text.forEach {
            it.background = resources.getDrawable(R.drawable.rounded_empty_green)
            it.setTextColor(resources.getColor(R.color.white))
        }
        button.background = resources.getDrawable(R.drawable.rounded_green)
        button.setTextColor(resources.getColor(R.color.black))
    }

    private fun recreate(){
        findNavController().navigate(
            R.id.leaderboardFragment,
            arguments,
            NavOptions.Builder()
                .setPopUpTo(R.id.leaderboardFragment,true)
                .build()
        )
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}