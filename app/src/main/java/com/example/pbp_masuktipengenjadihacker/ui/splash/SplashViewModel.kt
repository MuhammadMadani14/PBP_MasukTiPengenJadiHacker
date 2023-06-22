package com.example.pbp_masuktipengenjadihacker.ui.splash

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.pbp_masuktipengenjadihacker.R
import com.google.firebase.auth.FirebaseAuth


class SplashViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    fun currentUser(navController: NavController){
        val currentUser = auth.currentUser
        if (currentUser == null) navController.navigate(R.id.action_splashFragment_to_loginFragment)
        else reload(navController)
    }

    private fun reload(navController:NavController){
        auth.currentUser?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful){
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
                FirebaseAuth.getInstance().signOut()
            }
        }
    }


}