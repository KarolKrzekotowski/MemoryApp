package com.example.memoryapp.ui.fragment.bottomSetup

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.memoryapp.databinding.BottomDialogBinding


class BottomDialog : BottomSheetDialogFragment() {

    private var _binding: BottomDialogBinding? = null
    private val binding get() = _binding!!
    private var size = 0
    private var category = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = BottomDialogBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.size16.setOnClickListener {
            size = 4
        }
        binding.size24.setOnClickListener {
            size = 6
        }
        binding.size32.setOnClickListener {
            size = 8
        }
        binding.startGame.setOnClickListener {
            if(size == 0){
                Toast.makeText(requireContext(),"Wybierz rozmiar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//            if(category == ""){
//                Toast.makeText(requireContext(),"Wybierz kategorię", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            if (binding.playerName.text.toString() == ""){
                Toast.makeText(requireContext(),"Brak nazwy gracza", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val playerName = binding.playerName.text.toString()
            val action = BottomDialogDirections.actionBottomDialogFragmentToGameFragment(
                size,playerName,category,
            )
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}