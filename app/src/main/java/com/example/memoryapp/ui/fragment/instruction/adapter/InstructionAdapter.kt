package com.example.memoryapp.ui.fragment.instruction.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.memoryapp.ui.fragment.instruction.pages.Instruction0Fragment
import com.example.memoryapp.ui.fragment.instruction.pages.Instruction1Fragment
import com.example.memoryapp.ui.fragment.instruction.pages.Instruction2Fragment

class InstructionAdapter(fragment:Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Instruction0Fragment()
            1 -> Instruction1Fragment()
            2-> Instruction2Fragment()
            else -> createFragment(position)
        }
    }

}