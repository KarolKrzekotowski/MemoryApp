package com.example.memoryapp.ui.fragment.leaderboard.local

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoryapp.databinding.FragmentLocalRankBinding
import com.example.memoryapp.ui.fragment.leaderboard.LeaderboardViewmodel
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LocalRankFragment : Fragment() {
    private val viewModel by activityViewModels<LeaderboardViewmodel>()
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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.tempLocal.collect{
                        adapter.setData(it)
                    }

                }
                launch {
                    viewModel.resultType.collect{
                        adapter.setResult(it)
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