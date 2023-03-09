package com.example.memoryapp

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.memoryapp.databinding.BottomDialogBinding


class BottomDialog : BottomSheetDialogFragment() {

    private var _binding: BottomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = BottomDialogBinding.inflate(inflater, container, false)
        binding.button4x4.setOnClickListener {
            val action = BottomDialogDirections.actionBottomDialogFragmentToGameFragment(4)
            findNavController().navigate(action)
        }
        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}