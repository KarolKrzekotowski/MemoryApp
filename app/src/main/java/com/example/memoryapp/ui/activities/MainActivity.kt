package com.example.memoryapp.ui.activities

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.memoryapp.R
import com.example.memoryapp.game.hideUI

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideUI()
        instance = this
//        setTheme(R.style.Theme_MemoryApp)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.isLooping = true
    }

    companion object{
        private lateinit var instance : MainActivity
        lateinit var mediaPlayer: MediaPlayer


    }

}