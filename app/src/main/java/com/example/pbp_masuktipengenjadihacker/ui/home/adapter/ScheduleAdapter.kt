package com.example.pbp_masuktipengenjadihacker.ui.home.adapter


import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pbp_masuktipengenjadihacker.R
import com.example.pbp_masuktipengenjadihacker.data.DataTeam
import com.example.pbp_masuktipengenjadihacker.databinding.ItemHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.*


class ScheduleAdapter(private val listSchedule: List<DataTeam>): RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemHomeBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("MissingInflatedId")
        fun bind(item: DataTeam) {
            binding.apply {
                Glide.with(itemView.context).load(item.image).into(ivItem)
                setTeamHome.text = item.nama_team.toString()
                setTanggal.text = item.jadwal.toString()
                setStatistik.text = item.statistik_pemain.toString()
                setSquadlist.text = item.squad_list.toString()
                root.setOnClickListener {
                    val content = LayoutInflater.from(
                        itemView.context
                    ).inflate(R.layout.custom_view, null)
                    val img = content.findViewById<ImageView>(R.id.ivItem)
                    val nameTeam = content.findViewById<MaterialTextView>(R.id.setTeamHome)
                    val tanggal = content.findViewById<MaterialTextView>(R.id.setTanggal)
                    val statistik = content.findViewById<MaterialTextView>(R.id.setStatistik)
                    val squadList = content.findViewById<MaterialTextView>(R.id.setSquadlist)

                    MaterialAlertDialogBuilder(itemView.context).setView(
                        content
                    ).show()
                }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(listSchedule[position])

    override fun getItemCount(): Int = listSchedule.size
}