package com.example.memoryapp.game

import android.animation.AnimatorSet
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryapp.databinding.CardItemBinding
import kotlinx.coroutines.*

class BoardAdapter (
    private val sizeOfMap: Int,
    private val cards: List<Card>,
    private val front : AnimatorSet,
    private val back : AnimatorSet
                    ): RecyclerView.Adapter<BoardAdapter.ViewHolder>()
{
    private lateinit var context:Context
    private lateinit var binding: CardItemBinding
    private var es = mutableListOf<Pair<ViewHolder,Card>>()
    private var points = 0
    private var checking = false
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var playingCard: ImageSwitcher = binding.imageView2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = binding.root.context
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        holder.playingCard.setFactory {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            imageView
        }
        holder.playingCard.setImageResource(card.imageBack)
        val inAnimation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left)
        val outAnimation = AnimationUtils.loadAnimation(context,android.R.anim.slide_out_right)
        holder.playingCard.inAnimation = inAnimation
        holder.playingCard.outAnimation = outAnimation
        holder.playingCard.setImageResource(card.imageBack)

        holder.playingCard.setOnClickListener {
            if (card.isMatched || checking){
                return@setOnClickListener
            }
            es.add(Pair(holder,card))
            if (!card.isFaceUp ){
                holder.playingCard.setImageResource(card.image)
                card.isFaceUp = true
            }
            if (es.size == 2){
                CoroutineScope(Dispatchers.Main).launch {
                    checking = true
                    delay(500L)

                    checkMatch()

                }

            }
        }
    }
    private fun checkMatch(){

        if (es[0].second.image == es[1].second.image){
            es[0].second.isMatched = true
            es[1].second.isMatched = true
            points++
            checkWin()
        }
        else{
            es[0].first.playingCard.setImageResource(es[0].second.imageBack)
            es[0].second.isFaceUp = false
            es[1].first.playingCard.setImageResource(es[1].second.imageBack)
            es[1].second.isFaceUp = false

        }
        es.clear()
        checking = false
    }

    private fun checkWin(){
        if (points == sizeOfMap/2){
            GameFragment.victory()
        }
    }
}
