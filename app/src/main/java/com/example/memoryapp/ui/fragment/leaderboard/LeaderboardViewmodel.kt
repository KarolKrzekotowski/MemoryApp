package com.example.memoryapp.ui.fragment.leaderboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.memoryapp.data.db.entities.LeaderboardEntity
import com.example.memoryapp.repository.LeaderboardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LeaderboardViewmodel(application: Application):AndroidViewModel(application) {

    private val repository = LeaderboardRepository(application)

    private val _globalLeaderboard = MutableStateFlow<List<LeaderboardEntity>?>(emptyList())
    val globalLeaderboard = _globalLeaderboard.asStateFlow()

    private val _localLeaderboard = MutableStateFlow<List<LeaderboardEntity>?>(emptyList())
    val localLeaderboard = _localLeaderboard.asStateFlow()

    private val _tempLocal = MutableStateFlow<List<LeaderboardEntity>>(emptyList())
    val tempLocal = _tempLocal.asStateFlow()

    private val _tempGlobal = MutableStateFlow<List<LeaderboardEntity>>(emptyList())
    val tempGlobal = _tempGlobal.asStateFlow()

    private val _resultType = MutableStateFlow(true)
    val resultType = _resultType.asStateFlow()
    var time = true
    var size = 16




    fun filterSize(size: Int){
        this.size = size
        _tempLocal.update { localLeaderboard.value!!.filter { it.level == size } }
        _tempGlobal.update { globalLeaderboard.value!!.filter { it.level == size } }
        filterReference(time)
    }



    fun filterReference(time : Boolean){
        this.time = time
        if (time){
            _tempLocal.update { tempLocal.value.sortedBy { it.time }}
            _tempGlobal.update { tempGlobal.value.sortedBy { it.time } }
        }
        else{
            _tempLocal.update { tempLocal.value.sortedBy { it.tries }}
            _tempGlobal.update { tempGlobal.value.sortedBy { it.tries } }
        }
        _resultType.update { time }
    }
    fun loadRemoteData(){
        viewModelScope.launch {

            var remote = repository.getAllRemote()
            if (remote!=null){

                remote = remote.sortedBy{ it.time }

                _globalLeaderboard.update { remote }
            }
        }
    }

    fun loadLocalData(){

        viewModelScope.launch {
            var local = repository.getAllLocal().first()
            if (local!=null){
                _localLeaderboard.update { local }
            }

        }
    }
    fun loadData(){
        viewModelScope.launch {
            loadRemoteData()
            loadLocalData()
        }
        viewModelScope.launch{
            localLeaderboard.collect{
                _tempLocal.update { it }
                filterSize(16)
            }
        }
        viewModelScope.launch{
            globalLeaderboard.collect{
                _tempGlobal.update { it }
                filterSize(16)
            }
        }

    }
}