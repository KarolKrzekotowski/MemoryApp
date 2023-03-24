package com.example.memoryapp.ui.fragment.leaderboard.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryapp.data.db.entities.LeaderboardEntity

import com.example.memoryapp.databinding.LeaderboardItemBinding

class RankRecyclerViewAdapter : RecyclerView.Adapter<RankRecyclerViewAdapter.ViewHolder>() {

    private var leaderboardList = emptyList<LeaderboardEntity>()
    private lateinit var binding: LeaderboardItemBinding
    private var time = true
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var name = binding.name
        var level = binding.level
        var result = binding.result
        var place = binding.place
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = LeaderboardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = leaderboardList[position]
        with(holder){
            name.text = data.name
            level.text = data.level.toString()
            place.text = (position+1).toString()
        }
        if (time){
            holder.result.text = data.time
        }
        else{
            holder.result.text = data.tries.toString()
        }


    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }

    fun setResult(result: Boolean){
        time = result
        notifyDataSetChanged()
    }
    fun setData(leaderboardList: List<LeaderboardEntity>){
        this.leaderboardList = leaderboardList
        notifyDataSetChanged()
    }
}