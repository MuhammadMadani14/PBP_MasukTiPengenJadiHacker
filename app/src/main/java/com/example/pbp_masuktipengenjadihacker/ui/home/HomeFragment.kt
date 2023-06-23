package com.example.pbp_masuktipengenjadihacker.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbp_masuktipengenjadihacker.R

import com.example.pbp_masuktipengenjadihacker.databinding.FragmentHomeBinding
import com.example.pbp_masuktipengenjadihacker.ui.bottom_sheet.BottomSheetFragment
import com.example.pbp_masuktipengenjadihacker.ui.home.adapter.ScheduleAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val homeVM: HomeViewModel by viewModels()

    companion object {
        fun newInstance() = HomeFragment()
    }

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
        observeSchedule()
    }

    private fun observeSchedule(){
        homeVM.getData()
        homeVM.schedule.observe(viewLifecycleOwner){
            if(it != null){
                binding.apply {
                    rvHome.adapter = ScheduleAdapter(it)
                    rvHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }
            }
        }
    }

 

}