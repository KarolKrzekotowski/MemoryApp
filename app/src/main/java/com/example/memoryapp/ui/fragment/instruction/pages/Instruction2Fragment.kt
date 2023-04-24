package com.example.memoryapp.ui.fragment.instruction.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentInstruction2Binding
import android.net.Uri.parse



class Instruction2Fragment : Fragment() {

    private var _binding: FragmentInstruction2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstruction2Binding.inflate(inflater,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)

        val uri = parse("android.resource://"+requireContext().packageName+"/"+R.raw.giera)
        binding.videoView.apply {
            setMediaController(mediaController)
            setVideoURI(uri)
            requestFocus()
            start()
        }
        binding.videoView

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}