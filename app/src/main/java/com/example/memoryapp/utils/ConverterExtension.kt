package com.example.memoryapp.utils

import android.graphics.LinearGradient
import android.graphics.Shader
import android.widget.TextView
import com.example.memoryapp.R
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

fun TextView.textGradient(){
    val paint = this.paint
    val colors = intArrayOf(resources.getColor(R.color.green_gradient),resources.getColor(R.color.red_gradient))
    val position = floatArrayOf(0F,1F)
    val width = paint.measureText(this.text.toString())
    val textShader = LinearGradient(
        0F,
        0F,
        width,
        0F,
        colors,
        position,
        Shader.TileMode.CLAMP)
    this.paint.shader = textShader
}