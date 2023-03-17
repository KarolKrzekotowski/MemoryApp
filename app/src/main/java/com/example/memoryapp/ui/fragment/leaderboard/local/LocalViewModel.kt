package com.example.memoryapp.ui.fragment.leaderboard.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.repository.LeaderboardRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocalViewModel(application: Application):AndroidViewModel(application) {

    private val repository = LeaderboardRepository(application)
    private val _leaderboard = MutableStateFlow<List<Leaderboard>?>(emptyList())
    val leaderboard = _leaderboard.asStateFlow()

    fun loadData() {
        var local: List<Leaderboard>
        viewModelScope.launch {
            local = repository.getAllLocal().first()
            if (local!=null){
                _leaderboard.update { local }
            }

        }

    }

}