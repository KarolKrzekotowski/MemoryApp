package com.example.memoryapp.game

import android.app.ActionBar.LayoutParams
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentGameBinding

class GameFragment : Fragment() {

    val args: GameFragmentArgs by navArgs()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var tableLayout: TableLayout
    private var sizeOfMap = 0
    private val cards = mutableListOf<Card>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sizeOfMap = args.sizeOfMap
        _binding = FragmentGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0 until sizeOfMap){
            cards.add(Card(R.drawable.card_back_black,false,false, "${i}0".toInt()))
            cards.add(Card(R.drawable.card_back_black,false,false,"${i}1".toInt()))
            cards.add(Card(R.drawable.card_back_black,false,false,"${i}2".toInt()))
            cards.add(Card(R.drawable.card_back_black,false,false,"${i}3".toInt()))
        }
        val adapter = BoardAdapter(
            context = requireContext(),
            boardSize = sizeOfMap,
            cards = cards,
            onCardClick = { card ->
                flip()
            }
        )
        binding.rvBoard.adapter = adapter
//        binding.rvBoard.layoutManager = GridLayoutManager(requireContext(), sizeOfMap)
        binding.rvBoard.layoutManager = object  : GridLayoutManager(requireContext(),sizeOfMap){
            override fun canScrollHorizontally(): Boolean {
                return false
            }

            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    private fun flip(){

    }




}