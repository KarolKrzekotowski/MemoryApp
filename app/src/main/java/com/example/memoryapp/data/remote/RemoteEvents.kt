package com.example.memoryapp.data.remote

import android.provider.Settings
import android.util.Log
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.ui.activities.MainActivity
import com.example.memoryapp.ui.fragment.leaderboard.adapter.RankRecyclerViewAdapter
import com.example.memoryapp.utils.toLocal
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.collections.List

class RemoteEvents : DataSource {
    override fun uploadResult(leaderboard: LeaderboardRemote, uniqueID: String) {
        myRef.child(uniqueID).child(leaderboard.name.toString() + leaderboard.level.toString())
            .setValue(leaderboard)
    }

    override suspend fun getAllRemote(): List<Leaderboard> {

        val abc = mutableListOf<Leaderboard>()
        val temp = mutableListOf<String>()


        myRef.get().addOnSuccessListener {

            for (i in it.children) {
                for (b in i.children) {
                    for (c in b.children) {
                        temp.add(c.value.toString())
                    }
                    abc.add(temp.toLocal())
                    temp.clear()
                }
            }
        }.await()
        return abc
    }

    companion object {
        val database =
            Firebase.database("https://memoryapp-7d0f6-default-rtdb.europe-west1.firebasedatabase.app/")
        val myRef = database.reference

    }
}