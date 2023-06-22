package com.example.pbp_masuktipengenjadihacker.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pbp_masuktipengenjadihacker.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding : FragmentSplashBinding
    private val splashVM : SplashViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        Handler(Looper.getMainLooper()).postDelayed({
            splashVM.currentUser(findNavController())
        },3000)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}