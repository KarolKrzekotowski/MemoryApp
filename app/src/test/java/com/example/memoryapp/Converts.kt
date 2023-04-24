package com.example.memoryapp

import com.example.memoryapp.data.db.entities.LeaderboardEntity
import com.example.memoryapp.data.model.LeaderboardRemote
import com.example.memoryapp.utils.toLocal
import com.example.memoryapp.utils.toRemote
import org.junit.Assert.assertEquals
import org.junit.Test

class Converts {
    @Test
    fun `firebase to local is correct`(){
        val fire = LeaderboardRemote(
            "name",
            16,
            "00:10:12",
            84
        )
        val converted = fire.toLocal()
        val correct = LeaderboardEntity(
            0,
            "name",
            16,
            "00:10:12",
            84)
        assertEquals(converted , correct)
    }
    @Test
    fun `local to firebase is correct`(){
        val local = LeaderboardEntity(
            0,
            "name",
            16,
            "00:10:12",
            84)
        val fire = local.toRemote()
        val correct = LeaderboardRemote(
            "name",
            16,
            "00:10:12",
            84
        )
        assertEquals(fire,correct)
    }
}