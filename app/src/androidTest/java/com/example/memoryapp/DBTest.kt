package com.example.memoryapp

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memoryapp.data.db.LeaderboardDatabase
import com.example.memoryapp.data.db.dao.LeaderboardDao
import com.example.memoryapp.data.db.entities.LeaderboardEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DBTest {
    private lateinit var dao: LeaderboardDao
    private lateinit var db: LeaderboardDatabase


    @Before
    fun createDatabase(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LeaderboardDatabase::class.java).build()
        dao = db.leaderboardDao()

    }
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeReadResult(){
        val input = LeaderboardEntity(0,"Karolina",16,"00:01:30",28)
        runBlocking {
            val trashInput = LeaderboardEntity(0,"hej",19,"00:00:01",15)
            repeat(100){
                dao.insertPlayer(trashInput)
            }
            dao.insertPlayer(input)
        }
        val output : List<LeaderboardEntity>
        runBlocking{
            output = db.leaderboardDao().getAllLocal().first()
        }
        val outputResult = output[output.size-1]
        assert(output.size == outputResult.id)
        assert(input.level == outputResult.level)
        assert(input.tries == outputResult.tries)
        assert(input.time == outputResult.time)
        assert(input.name == outputResult.name)
    }


}




