package com.example.memoryapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.memoryapp.databinding.BottomDialogBinding
import com.example.memoryapp.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    val args: GameFragmentArgs by navArgs()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var sizeOfMap = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sizeOfMap = args.sizeOfMap
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.size.text = sizeOfMap.toString()
    }


}