package com.example.memoryapp.utils

import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.data.model.LeaderboardRemote

fun Leaderboard.toRemote():LeaderboardRemote{
    return LeaderboardRemote(name, level, time)
}

fun LeaderboardRemote.toLocal():Leaderboard{
    return Leaderboard(0,name!!,level!!,time!!)
}

fun MutableList<String>.toLocal():Leaderboard{
    return Leaderboard(0,this[1],this[0].toInt(),this[2])
}