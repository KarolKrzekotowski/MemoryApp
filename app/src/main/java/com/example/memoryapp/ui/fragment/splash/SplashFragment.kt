package com.example.memoryapp.ui.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.os.bundleOf
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        val extras = FragmentNavigatorExtras(binding.imageView8 to "image_big")
        Handler(Looper.getMainLooper()).postDelayed({
            val bundle = bundleOf("victory" to false )
            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_mainMenuFragment,
                    bundle,
                    null,
                    extras
                )

        }, 1000L)



        // Inflate the layout for this fragment
        return binding.root
    }


}