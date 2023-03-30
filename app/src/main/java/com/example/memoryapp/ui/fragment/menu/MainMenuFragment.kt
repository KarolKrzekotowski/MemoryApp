package com.example.memoryapp.ui.fragment.menu

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.memoryapp.R
import com.example.memoryapp.data.db.entities.LeaderboardEntity
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.databinding.FragmentMainMenuBinding
import com.example.memoryapp.game.hideUI
import com.example.memoryapp.repository.LeaderboardRepository
import com.example.memoryapp.ui.activities.MainActivity
import com.example.memoryapp.utils.textGradient
import com.example.memoryapp.utils.translate
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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
            Navigation.findNavController(binding.root).navigate(MainMenuFragmentDirections.actionMainMenuFragmentToBottomDialogFragment())
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
            Toast.makeText(requireContext(),"Gratulacje", Toast.LENGTH_SHORT).show()
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