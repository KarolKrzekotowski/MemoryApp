package com.example.memoryapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Local_Leaderboard")
data class Leaderboard (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name : String,
    val level : Int,
    val time : String
        )