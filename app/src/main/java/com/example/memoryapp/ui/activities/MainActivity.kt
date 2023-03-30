package com.example.memoryapp.ui.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Secure
import com.example.memoryapp.R
import com.example.memoryapp.game.hideUI
import com.google.android.gms.ads.identifier.AdvertisingIdClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideUI()
        instance = this
        setContentView(R.layout.activity_main)
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer.isLooping = true
    }

    companion object{
        private lateinit var instance : MainActivity
        lateinit var mediaPlayer: MediaPlayer


    }

}