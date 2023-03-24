package com.example.memoryapp.repository

import android.content.Context
import android.provider.Settings
import com.example.memoryapp.data.db.LeaderboardDatabase
import com.example.memoryapp.data.db.entities.LeaderboardEntity
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.data.remote.RemoteEvents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LeaderboardRepository(private val context: Context) {

    private val dao = LeaderboardDatabase.getInstance(context).leaderboardDao()
    private val fire = RemoteEvents()
    suspend fun insertPlayer(leaderboard: LeaderboardEntity) =
        withContext(Dispatchers.IO){
            dao.insertPlayer(leaderboard)
        }
    fun getAllLocal() : Flow<List<LeaderboardEntity>> {
        return dao.getAllLocal()

    }
    suspend fun getAllRemote():List<LeaderboardEntity>{
        return fire.getAllRemote()
    }

    fun updateRemote(leaderboard: LeaderboardRemote){
        val b = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        fire.uploadResult(leaderboard,b!!)

    }
}