package com.example.memoryapp.ui.fragment.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentMainMenuBinding
import com.example.memoryapp.game.hideUI
import com.example.memoryapp.ui.activities.MainActivity
import com.example.memoryapp.utils.textGradient
import com.example.memoryapp.utils.translate


class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding
    private val args: MainMenuFragmentArgs by navArgs()
    private val mediaPlayer = MainActivity.mediaPlayer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding.language.textGradient()

        binding.instruction.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(MainMenuFragmentDirections.actionMainMenuFragmentToInstructionFragment())
        }

        binding.play.setOnClickListener {
            findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragmentToBottomDialogFragment())
        }
        binding.leaderboards.setOnClickListener {
            val action = MainMenuFragmentDirections.actionMainMenuFragmentToLeaderboardFragment()
            findNavController().navigate(action)
        }


        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation

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
//        CoroutineScope(Dispatchers.IO).launch {
//            val repository = LeaderboardRepository(requireContext())
//            var i = 10
////            while (i<45){
//
//                repository.insertPlayer(LeaderboardEntity(0,"Karoldlugabardzonazwa$i",24,"00:05:0$i",15+i))
//
////        i++
////        }
//
//        }

        binding.language.setOnClickListener {
            translate()
            recreate()
        }
        if (args.victory){
            Toast.makeText(requireContext(),R.string.ToastVictory, Toast.LENGTH_SHORT).show()
        }
        hideUI()

        return binding.root
    }


    private fun recreate(){
        findNavController().navigate(
            R.id.mainMenuFragment,
            arguments,
            NavOptions.Builder()
                .setPopUpTo(R.id.mainMenuFragment,true)
                .build()
        )
    }





}