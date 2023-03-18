package com.example.memoryapp.ui.fragment.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryapp.R
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.data.model.Card
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.repository.LeaderboardRepository
import com.example.memoryapp.game.cardsArray
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class GameViewModel(application: Application): AndroidViewModel(application) {

    private val repository = LeaderboardRepository(application)

    private val _shuffledCards = MutableStateFlow(mutableListOf<Card>())
    val shuffledCards = _shuffledCards.asStateFlow()

    var running = true
    private var hours :Int = 0
    private var minutes : Int = 0
    private var seconds : Int = 0
    var isPlaying = false

    val countUpFlow = flow<String> {
        while (running){
            delay(1000L)
            increaseTime()

            val timer = repairTimes()
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
        var s = ""
        var h = ""
        var m = ""
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
    fun prepareCards(sizeOfMap:Int){
        viewModelScope.launch {
            val cards = mutableListOf<Card>()
            cardsArray.shuffle()
            val temporaryCards = mutableListOf<Int>()

            temporaryCards.addAll(cardsArray.take(sizeOfMap*2))
            for (i in 0 until sizeOfMap*2){
                cards.add(Card(temporaryCards[i], R.drawable.card_back_black,false,false))
                cards.add(Card(temporaryCards[i], R.drawable.card_back_black,false,false))
            }
            cards.shuffle()
            _shuffledCards.update {
                cards
            }
        }
    }
    fun insertResultToDb(leaderboard: Leaderboard){
        viewModelScope.launch{
            repository.insertPlayer(leaderboard)}
        viewModelScope.launch {
            val kwas = LeaderboardRemote(
                name =leaderboard.name,
                time = leaderboard.time,
                level = leaderboard.level
            )
            repository.updateRemote(kwas)
        }
    }
}