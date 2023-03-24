package com.example.memoryapp.utils

import com.example.memoryapp.data.db.entities.LeaderboardEntity
import com.example.memoryapp.data.model.LeaderboardRemote

fun LeaderboardEntity.toRemote():LeaderboardRemote{
    return LeaderboardRemote(name, level, time,tries)
}

fun LeaderboardRemote.toLocal():LeaderboardEntity{
    return LeaderboardEntity(0,name!!,level!!,time!!,tries!!)
}

fun MutableList<String>.toLocal():LeaderboardEntity{
    return LeaderboardEntity(0,this[1],this[0].toInt(),this[2],this[3].toInt())
}