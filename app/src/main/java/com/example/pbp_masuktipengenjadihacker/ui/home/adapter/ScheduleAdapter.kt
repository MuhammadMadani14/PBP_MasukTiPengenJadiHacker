package com.example.pbp_masuktipengenjadihacker.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pbp_masuktipengenjadihacker.data.DataTeam
import com.example.pbp_masuktipengenjadihacker.databinding.ItemHomeBinding

class ScheduleAdapter(private val listSchedule: List<DataTeam>): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: DataTeam){
            binding.apply {
                setTeamHome.text = item.nama_tim
                setTanggal.text = item.jadwal
                setSquadlist.text = item.squad_list
                setStatistik.text = item.statistik_pemain
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(listSchedule[position])

    override fun getItemCount(): Int = listSchedule.size
}