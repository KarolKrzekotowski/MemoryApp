package com.example.memoryapp.ui.fragment.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoryapp.databinding.FragmentGameBinding
import com.example.memoryapp.game.hideUI
import com.example.memoryapp.utils.textGradient
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class GameFragment : Fragment() {

    val args: GameFragmentArgs by navArgs()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var sizeOfMap = 0
    private val viewModel by viewModels<GameViewModel>()
    private var gameTime = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sizeOfMap = args.sizeOfMap
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        hideUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.sizeOfMap = sizeOfMap
        viewModel.playerName = args.playerName
        binding.apply {
            textView2.textGradient()
            textView5.textGradient()
            moves.textGradient()
            clock.textGradient()
        }
        val adapter = BoardAdapter(
            onTryClick = {viewHolder, card ->
                viewModel.insertToPair(Pair(viewHolder,card))
            }
            )
        binding.rvBoard.adapter = adapter
        binding.rvBoard.layoutManager = object  : GridLayoutManager(requireContext(),4,GridLayoutManager.VERTICAL, false){
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
                launch {
                    viewModel.countUpFlow.collect{ data ->
                        binding.clock.text = data
                        gameTime = data
                    }
                }
                if (!viewModel.isPlaying){
                    viewModel.prepareCards(sizeOfMap)
                    viewModel.isPlaying =true
                }
                launch {
                    viewModel.shuffledCards.collect{data ->
                        adapter.setData(data)
                    }
                }
                launch {
                    viewModel.checking.collect{bool ->
                        adapter.setState(bool)
                    }
                }
                launch {
                    viewModel.tries.collect{tries ->
                        binding.moves.text = tries.toString()
                    }
                }

                launch {
                    viewModel.points.collect{
                        if (it == sizeOfMap*2){
                            val action =
                                GameFragmentDirections.actionGameFragmentToMainMenuFragment(true)
                            findNavController().navigate(action)
                        }
                        }
                    }
                }
        }
    }