package com.example.memoryapp.ui.fragment.leaderboard.global

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.repository.LeaderboardRepository
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GlobalViewModel(application: Application):AndroidViewModel(application) {

    private val repository = LeaderboardRepository(application)

    private val _leaderboard = MutableStateFlow<List<Leaderboard>?>(emptyList())
    val leaderboard = _leaderboard.asStateFlow()

    fun loadData(){
        viewModelScope.launch {

             var remote = repository.getAllRemote()
            if (remote!=null){

                 remote = remote.sortedBy { it.time }

                _leaderboard.update { remote }
            }
        }
    }



}