package com.example.memoryapp.ui.fragment.game

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
import com.example.memoryapp.data.model.Card
import kotlinx.coroutines.*

class BoardAdapter (
    private val onTryClick: (ViewHolder,Card) -> Unit
                    ): RecyclerView.Adapter<BoardAdapter.ViewHolder>()
{
    private lateinit var context:Context
    private lateinit var binding: CardItemBinding
    private var cards = emptyList<Card>()
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
            if (!card.isFaceUp ){
                holder.playingCard.setImageResource(card.image)
                card.isFaceUp = true
            }
            onTryClick(holder,card)

        }
    }
    fun setData(cards :List<Card>){
        this.cards = cards
        notifyDataSetChanged()
    }
    fun setState(boolean: Boolean){
        checking = boolean
    }
}
