package com.example.memoryapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.memoryapp.data.db.dao.LeaderboardDao
import com.example.memoryapp.data.db.entities.LeaderboardEntity

@Database(entities = [LeaderboardEntity::class], version = 1)
abstract class LeaderboardDatabase : RoomDatabase() {
    abstract fun leaderboardDao(): LeaderboardDao


    companion object {
        private var db: LeaderboardDatabase? = null
        fun getInstance(context: Context): LeaderboardDatabase {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    LeaderboardDatabase::class.java,
                    "leaderboard_database2"
                ).build()
            }
            return db!!
        }

    }
}