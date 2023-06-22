package com.example.pbp_masuktipengenjadihacker.ui.register

import android.os.Bundle
import android.os.PatternMatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.pbp_masuktipengenjadihacker.R
import com.example.pbp_masuktipengenjadihacker.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btn3.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty()){
                binding.edtEmail.error = "Email Dibutuhkan"
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmail.error = "Email Tidak sesuai"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.edtPassword.error = "Password dibutuhkan"
                return@setOnClickListener
            }
            if (password.length < 6){
                binding.edtPassword.error = "Password minimal 6 karakter"
                return@setOnClickListener
            }

            viewModel.registerFirebase(email,password)
        }

        viewModel.register.observe(viewLifecycleOwner){
            if (it.equals("Register Success!",true)){
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }else{
                Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


    }

}