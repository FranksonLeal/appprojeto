package com.example.task.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// TODO: Rename parameter arguments, choose names that match

class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks(){
        binding.btnRegister.setOnClickListener{ValidateData()}
    }
    private fun ValidateData(){
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()){
                binding.progressbar.isVisible = true
            }
            else {
                Toast.makeText(requireContext(), "Informe sua senha.", Toast.LENGTH_SHORT).show()

            }
        }
        else {
            Toast.makeText(requireContext(), "Informe seu email.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)

                } else {
                    binding.progressbar.isVisible = false

                }
            }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}