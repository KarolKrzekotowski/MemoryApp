package com.example.memoryapp.ui.fragment.bottomSetup

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
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
        sizePicker()
        categoryPicker()
        binding.startGame.setOnClickListener {
            if(size == 0){
                Toast.makeText(requireContext(),"Wybierz rozmiar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(category == ""){
                Toast.makeText(requireContext(),"Wybierz kategorię", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
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

    private fun sizePicker(){


        binding.apply {
            button16.setOnClickListener {
                size = 4
                sizeBackgroundRefresh()
                f16.background = resources.getDrawable(R.drawable.gradeint_button2)
            }
            button24.setOnClickListener {
                size = 5
                sizeBackgroundRefresh()
                f24.background = resources.getDrawable(R.drawable.gradeint_button2)

            }
            button32.setOnClickListener {
                size = 6
                sizeBackgroundRefresh()
                f32.background = resources.getDrawable(R.drawable.gradeint_button2)
            }

        }
    }

    private fun sizeBackgroundRefresh(){
        val buttonsList = mutableListOf<FrameLayout>()
        buttonsList.apply {
            add(binding.f16)
            add(binding.f24)
            add(binding.f32)
        }
        buttonsList.map { it.setBackgroundResource(R.drawable.ezik_white) }
    }
    private fun categoryPicker(){

        binding.apply {
            emoji.setOnClickListener {
                categoryBackgroundRefresh()
                category = "emoji"
                emo1.background = resources.getDrawable(R.drawable.gradeint_button2)
            }
            furniture.setOnClickListener {
                categoryBackgroundRefresh()
                category = "furniture"
                furn.background = resources.getDrawable(R.drawable.gradeint_button2)
            }
            space.setOnClickListener {
                categoryBackgroundRefresh()
                category = "space"
                plan.background = resources.getDrawable(R.drawable.gradeint_button2)
            }
            food.setOnClickListener {
                categoryBackgroundRefresh()
                category = "food"
                fo1.background = resources.getDrawable(R.drawable.gradeint_button2)
            }
        }
    }
    private fun categoryBackgroundRefresh(){
        val categoryList = mutableListOf<FrameLayout>()
        categoryList.apply {
            add(binding.emo1)
            add(binding.fo1)
            add(binding.furn)
            add(binding.plan)
        }
        categoryList.map { it.setBackgroundResource(R.drawable.ezik_white) }
    }
}