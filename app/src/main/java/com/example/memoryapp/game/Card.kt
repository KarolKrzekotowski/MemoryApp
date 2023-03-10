package com.example.memoryapp.game

data class Card(
        val image: Int = 0,
        var isFaceUp: Boolean = false,
        var isMatched: Boolean = false,
        var identifier : Int
        )