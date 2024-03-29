package com.example.memoryapp.ui.fragment.leaderboard.global

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
import com.example.memoryapp.databinding.FragmentGlobalRankBinding
import com.example.memoryapp.ui.fragment.leaderboard.LeaderboardViewmodel
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter
import kotlinx.coroutines.launch


class GlobalRankFragment : Fragment() {

    private var _binding: FragmentGlobalRankBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<LeaderboardViewmodel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlobalRankBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = RankRecyclerViewAdapter()
        binding.rvGlobal.adapter = adapter
        binding.rvGlobal.layoutManager = LinearLayoutManager(requireContext())
        viewLifecycleOwner.lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    viewModel.tempGlobal.collect{
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