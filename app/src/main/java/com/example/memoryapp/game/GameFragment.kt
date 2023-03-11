package com.example.memoryapp.game

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.memoryapp.R
import com.example.memoryapp.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    val args: GameFragmentArgs by navArgs()

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private var sizeOfMap = 0
    private val cards = mutableListOf<Card>()
    private lateinit var front_animation: AnimatorSet
    private lateinit var back_animation: AnimatorSet

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

            cards.add(Card(R.drawable.card_back_black,R.drawable.black_joker,false,false, "${i}0".toInt()))
            cards.add(Card(R.drawable.card_back_black,R.drawable.black_joker,false,false,"${i}1".toInt()))
            cards.add(Card(R.drawable.card_back_black,R.drawable.black_joker,false,false,"${i}2".toInt()))
            cards.add(Card(R.drawable.card_back_black,R.drawable.black_joker,false,false,"${i}3".toInt()))
        }
        front_animation = AnimatorInflater.loadAnimator(requireContext(),R.animator.front_anim) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(requireContext(),R.animator.back_animator) as AnimatorSet
        val adapter = BoardAdapter(

            boardSize = sizeOfMap,
            cards = cards,
            onCardClick = { card ->
                flip(card)

            },
            front = front_animation,
            back = back_animation

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

    }

    private fun flip(card: Card){

        if(card.isFaceUp)
        {
            front_animation.setTarget(card.image);
            back_animation.setTarget(card.imageBack);
            front_animation.start()
            back_animation.start()
            card.isFaceUp = false

        }
        else
        {
//            front_animation.setTarget(card.imageBack)
//
//            back_animation.setTarget(card.image)
//            back_animation.start()
//            front_animation.start()
            card.isFaceUp =true

        }


    }




}