package com.example.pbp_masuktipengenjadihacker.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pbp_masuktipengenjadihacker.data.DataTeam
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class HomeViewModel : ViewModel() {
    val firestore = FirebaseFirestore.getInstance()
    private var _schedule = MutableLiveData<List<DataTeam>>()
    val schedule: LiveData<List<DataTeam>> get() = _schedule
    private val dataList = mutableListOf<DataTeam>()

    fun getData() {
        firestore.collection("team")
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    Log.d("error", "No Data Found")
                }
                for (doc in it) {
                    val taskModel = doc.toObject(DataTeam::class.java)
                    dataList.add(taskModel)
                }
                _schedule.postValue(dataList)
            }
    }
}