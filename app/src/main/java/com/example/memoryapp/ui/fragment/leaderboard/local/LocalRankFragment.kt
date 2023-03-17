package com.example.memoryapp.ui.fragment.leaderboard.local

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoryapp.databinding.FragmentLocalRankBinding
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter
import kotlinx.coroutines.launch


class LocalRankFragment : Fragment() {
    private val viewModel by viewModels<LocalViewModel>()
    private var _binding: FragmentLocalRankBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocalRankBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RankRecyclerViewAdapter()
        binding.rvLocal.adapter = adapter
        binding.rvLocal.layoutManager = LinearLayoutManager(requireContext())
        viewModel.loadData()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.leaderboard.collect(){
                        adapter.setData(it!!)
                    }
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}