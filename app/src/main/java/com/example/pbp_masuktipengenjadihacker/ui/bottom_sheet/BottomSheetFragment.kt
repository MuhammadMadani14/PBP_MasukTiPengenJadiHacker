package com.example.pbp_masuktipengenjadihacker.ui.bottom_sheet

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pbp_masuktipengenjadihacker.R
import com.example.pbp_masuktipengenjadihacker.data.DataTeam
import com.example.pbp_masuktipengenjadihacker.databinding.FragmentBottomSheetBinding
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.UUID


class BottomSheetFragment :BottomSheetDialogFragment() {


        private val db =Firebase.firestore
        private var firebaseStorage: FirebaseStorage? = null
        private var firebaseReference : StorageReference? = null
        private val PICK_IMAGE_REQUEST = 71
        private var filePath : Uri? = null

        private lateinit var binding: FragmentBottomSheetBinding
    companion object {
        const val uploadToButton: String = "uploadButtonTag"

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

        firebaseStorage = FirebaseStorage.getInstance()
        firebaseReference = FirebaseStorage.getInstance().reference

        binding.ivUploadFoto.setOnClickListener{
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent,"Select Picture"),
                PICK_IMAGE_REQUEST
            )
        }
        addTeam()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath)
                binding.image.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun addTeam() {
        binding.apply {
            btnUpload.setOnClickListener {
                val namaTeam = nameTeam.text.toString().trim()
                val jadwal = jadwal.text.toString().trim()
                val squadList = squadlist.text.toString().trim()
                val statistikPemain = statistik.toString().trim()
                if (filePath != null && namaTeam.isNotEmpty() && jadwal.isNotEmpty() && squadList.isNotEmpty() && statistikPemain.isNotEmpty()){
                    val ref = firebaseReference?.child("uploads/"+ UUID.randomUUID().toString())
                    val uploadTask = ref?.putFile(filePath!!)
                    uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>{task ->
                        if (!task.isSuccessful){
                            task.exception?.let {
                                throw  it
                            }
                        }
                        return@Continuation ref.downloadUrl
                    })?.addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            val downloadUri = task.result
                            val dataTeam = hashMapOf(
                                "image" to downloadUri,
                                "jadwal" to jadwal,
                                "nama_team" to namaTeam,
                                "squad_list" to squadList,
                                "statistik_pemain" to statistikPemain,
                            )
                            db.collection("data_team")
                                .add(dataTeam)
                                .addOnSuccessListener {
//                                    Toast.makeText(requireContext(),"Success Upload",Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_bottomSheetFragment_to_homeFragment)
                                }.addOnFailureListener {
                                    Toast.makeText(requireContext(),"Failed Upload",Toast.LENGTH_SHORT).show()
                                }
                        }else{
                            Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                        }
                    }?.addOnFailureListener {
                        Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }
            }
        }
//        binding.apply {
//            btnUpload.setOnClickListener {
//                val namaTeam = nameTeam.text.toString().trim()
//                val jadwal = jadwal.text.toString().trim()
//                val squadList = squadlist.text.toString().trim()
//                val statistikPemain = statistik.toString().trim()
//                if (filePath != null && namaTeam.isNotEmpty() && jadwal.isNotEmpty() && squadList.isNotEmpty() && statistikPemain.isNotEmpty()) {
//                    val ref = firebaseReference?.child("uploads/" + UUID.randomUUID().toString())
//                    val uploadTask = ref?.putFile(filePath!!)
//                    uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
//                        if (!task.isSuccessful) {
//                            task.exception?.let {
//                                throw it
//                            }
//                        }
//                        return@Continuation ref.downloadUrl
//                    })?.addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            val downloadUri = task.result
//                            val product = hashMapOf(
//                                "photo" to downloadUri,
//                                "title" to title,
//                                "description" to description,
//                                "price" to price,
//                                "soldOut" to false,
//                            )
//                            db.collection("product")
//                                .add(product)
//                                .addOnSuccessListener {
//                                    FancyToast.makeText(
//                                        this@AddActivity,
//                                        "Add Product Success!",
//                                        FancyToast.LENGTH_SHORT,
//                                        FancyToast.SUCCESS,
//                                        false
//                                    ).show()
//                                    startActivity(
//                                        Intent(
//                                            this@AddActivity,
//                                            HomeActivity::class.java
//                                        )
//                                    )
//                                }.addOnFailureListener { e ->
//                                    FancyToast.makeText(
//                                        this@AddActivity,
//                                        e.message.toString(),
//                                        FancyToast.LENGTH_SHORT,
//                                        FancyToast.ERROR,
//                                        false
//                                    ).show()
//                                }
//                        } else {
//                            FancyToast.makeText(
//                                this@AddActivity,
//                                task.exception?.message.toString(),
//                                FancyToast.LENGTH_SHORT,
//                                FancyToast.ERROR,
//                                false
//                            ).show()
//                        }
//                    }?.addOnFailureListener {
//                        FancyToast.makeText(
//                            this@AddActivity,
//                            it.message.toString(),
//                            FancyToast.LENGTH_SHORT,
//                            FancyToast.ERROR,
//                            false
//                        ).show()
//                    }
//                } else {
//                    FancyToast.makeText(
//                        this@AddActivity,
//                        "Field must not empty!",
//                        FancyToast.LENGTH_SHORT,
//                        FancyToast.ERROR,
//                        false
//                    ).show()
//                }
//            }
//        }
    }


}