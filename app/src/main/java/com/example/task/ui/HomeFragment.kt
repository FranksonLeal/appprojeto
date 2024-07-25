package com.example.task.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentHomeBinding
import com.example.task.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        configTabLayout()
        initClicks()
    }


    private fun initClicks() {
        binding.ibLogout.setOnClickListener {
            showConfirmationDialog(
                title = "Confirmar saída",
                message = "Você tem certeza que deseja sair? Você precisará fazer login novamente.",
                onConfirm = { logoutapp() }
            )
        }
        binding.ibuser.setOnClickListener {
            showUserInfoDialog()
        }
    }


    private fun logoutapp() {
        auth.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_authetication)
    }

    private fun configTabLayout() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter
        adapter.addFragment(TodoFragment(), "Minhas anotações")


        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }


    private fun showConfirmationDialog(title: String, message: String, onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Sim") { dialog, which ->
            onConfirm()
        }
        builder.setNegativeButton("Não") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showUserInfoDialog() {
        val user = auth.currentUser
        user?.let {
            val email = user.email ?: "Email não disponível"
            val initial = user.displayName?.firstOrNull()?.toString() ?: user.email?.firstOrNull()?.toString() ?: "?"

            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_user_info, null)
            val emailTextView = dialogView.findViewById<TextView>(R.id.tvUserEmail)
            val initialTextView = dialogView.findViewById<TextView>(R.id.tvUserInitial)

            emailTextView.text = email
            initialTextView.text = initial

            AlertDialog.Builder(requireContext())
                .setTitle("Informações do Usuário")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
