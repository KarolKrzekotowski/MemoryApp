package com.example.memoryapp.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryapp.R
import com.example.memoryapp.databinding.CardItemBinding

class BoardAdapter (private val context: Context,
                    private val boardSize: Int,
                    private val cards: List<Card>,
                    private val onCardClick: (Card) -> Unit
                    ): RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    private lateinit var binding: CardItemBinding


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var handCard: ImageView = binding.imageButton
        init {
            handCard.setOnClickListener {
                onCardClick(cards[absoluteAdapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cards[position]
        val bitmapfactory = BitmapFactory.decodeResource(context.resources,card.image)
        val high = context.resources.displayMetrics.heightPixels *0.25
        val width = context.resources.displayMetrics.widthPixels * 0.1
        val bitmap = Bitmap.createScaledBitmap(bitmapfactory,width.toInt(),high.toInt(),false)
        // ustawienie karty
        holder.handCard.setImageBitmap(bitmap)
        holder.handCard.setBackgroundResource(R.drawable.card_back_black)
    }


}