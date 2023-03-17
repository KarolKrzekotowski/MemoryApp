package com.example.memoryapp.data.model

data class Card(
        val image: Int = 0,
        val imageBack : Int = 0,
        var isFaceUp: Boolean = false,
        var isMatched: Boolean = false
        )