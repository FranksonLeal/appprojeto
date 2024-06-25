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
import com.example.task.databinding.FragmentRecoverAccountBinding
import com.example.task.helper.FirebaseHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthRegistrar
import com.google.firebase.auth.auth

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [RecoverAccountFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecoverAccountFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecoverAccountBinding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initClicks(){
        binding.btnSend.setOnClickListener{
            ValidateData()
        }
    }

    private fun ValidateData(){
        val email = binding.edtEmail.text.toString().trim()

        if (email.isNotEmpty()) {
            binding.progressbar.isVisible = true
            recoverAccountUser(email)

        }
        else {
                Toast.makeText(requireContext(), "Informe sua senha.", Toast.LENGTH_SHORT).show()

            }
    }




    private fun recoverAccountUser(email:String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Pronto, acabamos de enviar um link para o seu email.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(
                        requireContext(),
                        FirebaseHelper.validError(task.exception?.message ?: ""),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.progressbar.isVisible = false
            }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
