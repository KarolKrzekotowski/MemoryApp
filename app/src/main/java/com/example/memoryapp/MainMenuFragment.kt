package com.example.memoryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.memoryapp.databinding.FragmentMainMenuBinding


class MainMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentMainMenuBinding
    private val args:  MainMenuFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        binding.textView.text = "bartek"
        binding.move.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(MainMenuFragmentDirections.actionMainMenuFragmentToInstructionFragment())
        }
        binding.button2.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(MainMenuFragmentDirections.actionMainMenuFragmentToBottomDialogFragment())
        }
        if (args.victory){
            Toast.makeText(requireContext(),"Gratulacje", Toast.LENGTH_SHORT).show()
        }
        // Inflate the layout for this fragment

        return binding.root
    }

}