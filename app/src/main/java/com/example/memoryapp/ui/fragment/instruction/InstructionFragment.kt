package com.example.memoryapp.ui.fragment.instruction


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentInstructionBinding
import com.example.memoryapp.ui.activities.MainActivity
import com.example.memoryapp.ui.fragment.instruction.adapter.InstructionAdapter
import com.example.memoryapp.utils.textGradient
import com.example.memoryapp.utils.translate
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class InstructionFragment : Fragment() {
    private var _binding: FragmentInstructionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var instructionAdapter: InstructionAdapter
    private val mediaPlayer = MainActivity.mediaPlayer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instructionAdapter = InstructionAdapter(this)
        viewPager = binding.viewpager
        viewPager.adapter = instructionAdapter

        val tabLayout = binding.tabLayout2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            //Some implementation
        }.attach()
        binding.goBack.setOnClickListener {
            val action = InstructionFragmentDirections.actionInstructionFragmentToMainMenuFragment(false)
            findNavController().navigate(action)
        }
        binding.langinst.setOnClickListener {
            translate()
            recreate()
        }
        if(mediaPlayer != null){
            if (mediaPlayer.isPlaying){
                binding.music.setImageResource(com.example.memoryapp.R.drawable.sound_on)
            }
            else{
                binding.music.setImageResource(com.example.memoryapp.R.drawable.sound_off)
            }
        }
        binding.langinst.textGradient()
        binding.music.setOnClickListener {

            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
                binding.music.setImageResource(com.example.memoryapp.R.drawable.sound_off)
            }
            else{
                mediaPlayer.start()
                binding.music.setImageResource(com.example.memoryapp.R.drawable.sound_on)
            }
        }
    }


    private fun recreate(){
        findNavController().navigate(
            R.id.instructionFragment,
            arguments,
            NavOptions.Builder()
                .setPopUpTo(R.id.instructionFragment,true)
                .build()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}