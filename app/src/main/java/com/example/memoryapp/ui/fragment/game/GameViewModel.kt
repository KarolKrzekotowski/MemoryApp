package com.example.memoryapp.ui.fragment.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryapp.R
import com.example.memoryapp.data.db.entities.LeaderboardEntity

import com.example.memoryapp.data.model.Card
import com.example.memoryapp.data.model.Category
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.game.*
import com.example.memoryapp.repository.LeaderboardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {

    private val repository = LeaderboardRepository(application)

    private val _shuffledCards = MutableStateFlow(mutableListOf<Card>())
    val shuffledCards = _shuffledCards.asStateFlow()

    private val _checking = MutableStateFlow(false)
    val checking = _checking.asStateFlow()

    private val _tries = MutableStateFlow(0)
    val tries = _tries.asStateFlow()

    private val _points = MutableStateFlow(0)
    val points = _points.asStateFlow()

    private val pairOfCards = mutableListOf<Pair<BoardAdapter.ViewHolder,Card>>()


    private var hours :Int = 0
    private var minutes : Int = 0
    private var seconds : Int = 0
    var running = true
    var isPlaying = false
    var sizeOfMap = 0
    var playerName = ""
    var timer = ""

    val countUpFlow = flow<String> {
        while (running){
            delay(1000L)
            increaseTime()

            timer = repairTimes()
            emit(timer)
        }
    }
    private fun increaseTime(){
        seconds++
        if (seconds == 60){
            minutes++
            seconds = 0
        }
        if (minutes == 60){
            hours++
            minutes = 0
        }
    }
    private fun repairTimes():String{
        val s: String
        val h: String
        val m:  String
        if (seconds<10){
            s = "0$seconds"
        }
        else{
            s = "$seconds"
        }
        if (minutes<10){
            m = "0$minutes"
        }
        else{
            m = "$minutes"
        }
        if (hours<10){
            h = "0$hours"
        }
        else{
            h = "$hours"
        }
        return "$h:$m:$s"
    }

    fun insertToPair(card :Pair<BoardAdapter.ViewHolder,Card>){
        pairOfCards.add(card)
        if (pairOfCards.size==2){
            viewModelScope.launch {
                _checking.update { true }
                delay(500L)
                increaseTries()
            }

        }
    }
    private fun increaseTries(){
        _tries.update { tries.value+1 }
        checkMatch()

    }

    private fun checkMatch(){


        if (pairOfCards[0].second.image == pairOfCards[1].second.image){
            pairOfCards[0].second.isMatched = true
            pairOfCards[1].second.isMatched = true
            _points.update { points.value +1 }
            checkWin()
        }
        else{
            pairOfCards[0].first.playingCard.setImageResource(pairOfCards[0].second.imageBack)
            pairOfCards[0].second.isFaceUp = false
            pairOfCards[1].first.playingCard.setImageResource(pairOfCards[1].second.imageBack)
            pairOfCards[1].second.isFaceUp = false

        }
        pairOfCards.clear()
        _checking.update { false }
    }

    private fun checkWin(){
        if (points.value == sizeOfMap*2){
            running = false
            insertResultToDb(
                LeaderboardEntity(
                    id = 0,
                    name = playerName,
                    time = timer,
                    level = sizeOfMap*4,
                    tries = tries.value
                )
            )

        }
    }


    fun prepareCards(sizeOfMap:Int,category: String){
        viewModelScope.launch {
            val cards = mutableListOf<Card>()
            val array :Array<Int> = when(category){
                "icon" -> iconArray
                "light" -> lightArray
                "block" -> rectangleArray
                else -> memoryArray
            }
            array.shuffle()
            val temporaryCards = mutableListOf<Int>()

            temporaryCards.addAll(array.take(sizeOfMap*2))
            for (i in 0 until sizeOfMap*2){
                cards.add(Card(temporaryCards[i], R.drawable.black_back,false,false))
                cards.add(Card(temporaryCards[i], R.drawable.black_back,false,false))
            }
            cards.shuffle()
            _shuffledCards.update {
                cards
            }
        }
    }
    fun insertResultToDb(leaderboard: LeaderboardEntity){
        viewModelScope.launch{
            repository.insertPlayer(leaderboard)}
        viewModelScope.launch {
            val kwas = LeaderboardRemote(
                name =leaderboard.name,
                time = leaderboard.time,
                level = leaderboard.level,
                tries = leaderboard.tries
            )
            repository.updateRemote(kwas)
        }
    }
}