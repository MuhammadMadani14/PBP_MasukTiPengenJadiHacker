package com.example.pbp_masuktipengenjadihacker.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.pbp_masuktipengenjadihacker.R
import com.example.pbp_masuktipengenjadihacker.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    lateinit var binding : FragmentLoginBinding
    private val viewModel : LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner){
            if (it!=null){
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }

        binding.tvBelumPunyaAkun.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btn3.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()

            if (email.isEmpty()){
                binding.edtEmail.error = "Email dibutuhkan"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtPassword.error = "Email invalid"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.edtPassword.error = "Password dibutuhkan"
                return@setOnClickListener
            }
            viewModel.loginFirebase(email,password)
        }

        viewModel.login.observe(viewLifecycleOwner) {
            if (it.equals("Login Success!", true)) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } else {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}