package com.example.task.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentSplashBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

private var _binding: FragmentSplashBinding? = null
private val binding get() = _binding!!
private  lateinit var auth: FirebaseAuth

class SplashFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentSplashBinding? = null
    private  val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed(this::checkAuth, 3000)
    }

    //função para checar se o usuario esta autenticado ou nao
    private fun checkAuth(){
        if (auth.currentUser==null){
            findNavController().navigate(R.id.action_splashFragment_to_authetication)
        }else{
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}