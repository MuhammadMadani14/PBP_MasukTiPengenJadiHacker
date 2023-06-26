package com.example.pbp_masuktipengenjadihacker.ui.home

import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pbp_masuktipengenjadihacker.R
import com.example.pbp_masuktipengenjadihacker.data.DataTeam

import com.example.pbp_masuktipengenjadihacker.databinding.FragmentHomeBinding
import com.example.pbp_masuktipengenjadihacker.ui.bottom_sheet.BottomSheetFragment
import com.example.pbp_masuktipengenjadihacker.ui.home.adapter.ScheduleAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private var auth : FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()
    private lateinit var dataTeam :ArrayList<DataTeam>
    val docs : ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonFloating.setOnClickListener {
            BottomSheetFragment().show(requireActivity().supportFragmentManager, BottomSheetFragment.uploadToButton)
        }

        binding.btnBack.setOnClickListener{
            Firebase.auth.signOut()
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        dataTeam = arrayListOf()
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        db.collection("data_team")
            .get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    var i = 0
                    for (data in it.documents) {
                        docs.add(it.documents[i].id)
                        val team = data.toObject(DataTeam::class.java)
                        Log.i("test",team?.jadwal.toString())
                        if (team != null) {
                            dataTeam.add(team)
                            i++
                        }
                    }
                    binding.rvHome.adapter = ScheduleAdapter(dataTeam)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Error Get Data Team",exception.message!!)
            }
    }
}