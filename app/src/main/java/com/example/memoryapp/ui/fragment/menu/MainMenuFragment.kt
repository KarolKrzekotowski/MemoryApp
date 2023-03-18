package com.example.memoryapp.ui.fragment.menu

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.memoryapp.R
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.databinding.FragmentMainMenuBinding
import com.example.memoryapp.game.hideUI
import com.example.memoryapp.repository.LeaderboardRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainMenuFragment : Fragment() {

    private lateinit var binding: FragmentMainMenuBinding
    private val args: MainMenuFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding.instruction.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(MainMenuFragmentDirections.actionMainMenuFragmentToInstructionFragment())
        }
        binding.play.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(MainMenuFragmentDirections.actionMainMenuFragmentToBottomDialogFragment())
        }
        binding.leaderboards.setOnClickListener {
            val action = MainMenuFragmentDirections.actionMainMenuFragmentToLeaderboardFragment()
            findNavController().navigate(action)
        }
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.music)
        binding.music.setOnClickListener {
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }
            else{
                mediaPlayer.isLooping = true
                mediaPlayer.start()
            }


        }
        if (args.victory){
            Toast.makeText(requireContext(),"Gratulacje", Toast.LENGTH_SHORT).show()
        }
        hideUI()

        return binding.root
    }

}