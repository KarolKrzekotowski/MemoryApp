package com.example.memoryapp.game

import android.animation.Animator
import android.animation.AnimatorSet
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryapp.R
import com.example.memoryapp.databinding.CardItemBinding

class BoardAdapter (
                    private val boardSize: Int,
                    private val cards: List<Card>,
                    private val onCardClick: (Card) -> Unit,
                    private val front : AnimatorSet,
                    private val back : AnimatorSet
                    ): RecyclerView.Adapter<BoardAdapter.ViewHolder>() {
    private lateinit var context:Context
    private lateinit var binding: CardItemBinding
    private val images = arrayOf(R.drawable.black_joker,R.drawable.card_back_black)

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var frontCard: ImageSwitcher = binding.imageView2


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


        holder.frontCard.setFactory {
            val imageView = ImageView(context)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            imageView
        }
        holder.frontCard.setImageResource(R.drawable.card_back_black)
        val inAnimation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left)
        val outAnimation = AnimationUtils.loadAnimation(context,android.R.anim.slide_out_right)
        holder.frontCard.inAnimation = inAnimation
        holder.frontCard.outAnimation = outAnimation
        holder.frontCard.setImageResource(R.drawable.black_joker)

        holder.frontCard.setOnClickListener {
            var ab = 0
            if (card.isFaceUp ){
                ab = 0
                card.isFaceUp = false
            }


            else{
                ab = 1
                card.isFaceUp = true
            }



            holder.frontCard.setImageResource(images[ab])
        }
        }



    }

//    private fun flip(card: Card){
//        if(card.isFaceUp)
//        {
//            front.setTarget(card.image);
//            back.setTarget(card.imageBack);
//            front.start()
//            back.start()
//            card.isFaceUp = false
//
//        }
//        else
//        {
//            front.setTarget(card.imageBack)
//
//            back.setTarget(card.image)
//            back.start()
//            front.start()
//            card.isFaceUp =true
//
//        }
