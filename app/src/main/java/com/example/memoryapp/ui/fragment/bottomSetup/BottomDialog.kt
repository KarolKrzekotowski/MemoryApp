package com.example.memoryapp.ui.fragment.bottomSetup

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.memoryapp.R
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


        val buttonsList = mutableListOf<Button>()
        buttonsList.apply {
            add(binding.button16)
            add(binding.button24)
            add(binding.button32)
//            map { it.background = resources.getDrawable(R.drawable.ezik) }
        }

        binding.apply {
            button16.setOnClickListener {
                size = 4
                buttonsList.map { it.backgroundTintList = requireContext().resources.getColorStateList(R.color.purple_500) }
                button16.backgroundTintList = requireContext().resources.getColorStateList(R.color.teal_200)
                binding.button16.background = resources.getDrawable(R.drawable.chosen_button)

            }
            button24.setOnClickListener {
                size = 6
                buttonsList.map { it.backgroundTintList = requireContext().resources.getColorStateList(R.color.purple_500) }
                button24.backgroundTintList = requireContext().resources.getColorStateList(R.color.teal_200)
                binding.button24.background = resources.getDrawable(R.drawable.chosen_button)
            }
            button32.setOnClickListener {
                size = 8
                buttonsList.map { it.backgroundTintList = requireContext().resources.getColorStateList(R.color.purple_500) }
                button32.backgroundTintList = requireContext().resources.getColorStateList(R.color.teal_200)
                binding.button32.background = resources.getDrawable(R.drawable.chosen_button)
            }

        }
        // text with gradient for 2 colors
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