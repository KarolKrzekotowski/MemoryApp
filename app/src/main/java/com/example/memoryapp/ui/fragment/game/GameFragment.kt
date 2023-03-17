package com.example.memoryapp.ui.fragment.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.databinding.FragmentGameBinding
import com.example.memoryapp.game.hideUI
import com.google.android.gms.ads.identifier.AdvertisingIdClient
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
        val adapter = BoardAdapter(
            sizeOfMap = sizeOfMap*4,
            onWinCardClick ={
                victory()
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

            repeatOnLifecycle(Lifecycle.State.STARTED){

                launch {
                    viewModel.countUpFlow.collect{ data ->
                        binding.clock.text = data
                        gameTime = data
                    }
                }

                launch {
                    viewModel.shuffledCards.collect{data ->
                        adapter.setData(data)
                    }
                }
            }
        }
        viewModel.prepareCards(sizeOfMap)
    }
private fun victory(){
        viewModel.running = false
        viewModel.insertResultToDb(
            Leaderboard(
                id = 0,
                name = args.playerName,
                time = gameTime,
                level = sizeOfMap*4
            )
        )
        val action =
            GameFragmentDirections.actionGameFragmentToMainMenuFragment(true)
        findNavController().navigate(action)
        }



}