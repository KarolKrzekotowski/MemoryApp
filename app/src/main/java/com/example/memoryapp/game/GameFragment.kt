package com.example.memoryapp.game

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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

    private val shuffledCards = mutableListOf<Int>()
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
        instance = this
        prepareCards()

        front_animation = AnimatorInflater.loadAnimator(requireContext(),R.animator.front_anim) as AnimatorSet
        back_animation = AnimatorInflater.loadAnimator(requireContext(),R.animator.back_animator) as AnimatorSet
        val adapter = BoardAdapter(

            sizeOfMap = sizeOfMap*4,
            cards = cards,
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

    private fun prepareCards(){
        cardsArray.shuffle()
        shuffledCards.addAll(cardsArray.take(sizeOfMap*2))

        for (i in 0 until sizeOfMap*2){
            cards.add(Card(shuffledCards[i],R.drawable.card_back_black,false,false))
            cards.add(Card(shuffledCards[i],R.drawable.card_back_black,false,false))
        }
        cards.shuffle()
    }

    private fun hideUI(){
        requireActivity().window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
//        if (requireActivity().support .supportActionBar != null) {
//            supportActionBar!!.hide();
//        }
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
    companion object{
        private lateinit var instance :GameFragment
        fun victory(){
            val action = GameFragmentDirections.actionGameFragmentToMainMenuFragment(true)
            Navigation.findNavController(instance.binding.root)
                .navigate(action)
        }
    }

}