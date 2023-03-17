package com.example.memoryapp.ui.activities

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
        setContentView(R.layout.activity_main)
    }

}