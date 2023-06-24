package com.example.pbp_masuktipengenjadihacker.ui.bottom_sheet

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pbp_masuktipengenjadihacker.R
import com.example.pbp_masuktipengenjadihacker.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class BottomSheetFragment :BottomSheetDialogFragment() {


    private lateinit var  storageRef : StorageReference

    private val db = Firebase.firestore
    private lateinit var binding: FragmentBottomSheetBinding
    companion object {
        const val uploadToButton: String = "uploadButtonTag"
        private const val IMAGE_PICK_REQUEST_CODE =123
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdload.setOnClickListener {
            val namaTim = binding.nameTeam.text.toString()
            val jadwal = binding.jadwal.text.toString()
            val squadList = binding.squadlist.text.toString()
            val statistik = binding.statistik.text.toString()
            if (namaTim.isNotEmpty() && jadwal.isNotEmpty() && squadList.isNotEmpty() && statistik.isNotEmpty()) {
                val team = hashMapOf(
                    "nama_tim" to namaTim,
                    "jadwal" to jadwal,
                    "squad_list" to squadList,
                    "statistik_pemain" to statistik,
                    "image" to "test"
                )


                db.collection("team")
                    .add(team)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Successfully added data", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.homeFragment)
                        this.dismiss()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to add data", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        //inisialisasi storage
        storageRef = FirebaseStorage.getInstance().getReference("uploads")

        //btnUploadGambar
        binding.uploadConstraint.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST_CODE && requestCode == Activity.RESULT_OK){
            //get img uri
            val imageUri : Uri? = data?.data

            //upload image to firebase
            imageUri?.let { uri ->
                val imageRef = storageRef.child("gs://pbp-masuktipengenjadihacker.appspot.com/${uri.lastPathSegment}")
                val uploadTask = imageRef.putFile(uri)

                //success atau tidak
                uploadTask.addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        Toast.makeText(requireContext(),"Success Upload",Toast.LENGTH_SHORT).show()
                    }else{
                        val exception = task.exception
                    }
                }
            }

        }
    }

}