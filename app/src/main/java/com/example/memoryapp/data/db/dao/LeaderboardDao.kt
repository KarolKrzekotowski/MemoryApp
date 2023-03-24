package com.example.memoryapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.memoryapp.data.db.entities.LeaderboardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeaderboardDao {

    @Query("SELECT * FROM local_leaderboard ORDER BY time")
    fun getAllLocal(): Flow<List<LeaderboardEntity>>

    @Insert
    suspend fun insertPlayer(leaderboard: LeaderboardEntity)
}