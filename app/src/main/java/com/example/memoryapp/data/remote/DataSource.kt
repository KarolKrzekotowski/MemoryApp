package com.example.memoryapp.data.remote


import com.example.memoryapp.data.db.entities.LeaderboardEntity
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter

interface DataSource {
    fun uploadResult(leaderboard: LeaderboardRemote,uniqueID:String)
    suspend fun getAllRemote(): List<LeaderboardEntity>
}