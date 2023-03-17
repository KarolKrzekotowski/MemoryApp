package com.example.memoryapp.repository

import android.content.Context
import android.util.Log
import com.example.memoryapp.data.db.LeaderboardDatabase
import com.example.memoryapp.data.db.dao.LeaderboardDao
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.data.remote.RemoteEvents
import com.example.memoryapp.ui.activities.MainActivity
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import android.provider.Settings
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter

class LeaderboardRepository(private val context: Context) {

    private val dao = LeaderboardDatabase.getInstance(context).leaderboardDao()
    private val fire = RemoteEvents()
    suspend fun insertPlayer(leaderboard: Leaderboard) =
        withContext(Dispatchers.IO){
            dao.insertPlayer(leaderboard)
        }
    fun getAllLocal() : Flow<List<Leaderboard>> {
        return dao.getAllLocal()

    }
    suspend fun getAllRemote():List<Leaderboard>{
        return fire.getAllRemote()
    }

    fun updateRemote(leaderboard: LeaderboardRemote){
        val b = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        fire.uploadResult(leaderboard,b!!)

    }
}