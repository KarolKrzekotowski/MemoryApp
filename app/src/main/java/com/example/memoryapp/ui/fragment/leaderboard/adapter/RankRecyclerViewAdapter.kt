package com.example.memoryapp.ui.fragment.leaderboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.memoryapp.data.db.entities.Leaderboard
import com.example.memoryapp.databinding.LeaderboardItemBinding

class RankRecyclerViewAdapter : RecyclerView.Adapter<RankRecyclerViewAdapter.ViewHolder>() {

    private var leaderboardList = emptyList<Leaderboard>()
    private lateinit var binding: LeaderboardItemBinding
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var name = binding.name
        var level = binding.level
        var time = binding.time
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
            time.text = data.time
            level.text = data.level.toString()
            place.text = (position+1).toString()
        }
    }

    override fun getItemCount(): Int {
        return leaderboardList.size
    }

    fun setData(leaderboardList: List<Leaderboard>){
        this.leaderboardList = leaderboardList
        notifyDataSetChanged()
    }
}