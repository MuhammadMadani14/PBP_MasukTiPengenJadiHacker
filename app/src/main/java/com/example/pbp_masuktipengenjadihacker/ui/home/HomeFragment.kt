package com.example.pbp_masuktipengenjadihacker.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pbp_masuktipengenjadihacker.R

import com.example.pbp_masuktipengenjadihacker.databinding.FragmentHomeBinding
import com.example.pbp_masuktipengenjadihacker.ui.bottom_sheet.BottomSheetFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding


    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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

    }

 

}