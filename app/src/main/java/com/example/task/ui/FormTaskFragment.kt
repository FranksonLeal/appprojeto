package com.example.task.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentFormTaskBinding
import com.example.task.model.Task
import com.example.task.helper.FirebaseHelper


class FormTaskFragment : Fragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    private lateinit var task: Task
    private var newTask: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        setupToolbar()
        initListeners()



        // Verifica se estamos editando uma tarefa existente
        val args = FormTaskFragmentArgs.fromBundle(requireArguments())
        if (args.task != null) {
            task = args.task!!
            newTask = false
            validateTask()
        } else {
            newTask = true
            task = Task() // Inicializa uma nova tarefa vazia
        }
    }

    private fun validateTask() {
        binding.textToolbar.text = getString(R.string.text_editing_task_form_task_fragment)
        binding.editDescription.setText(task.description)

    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener { validateData() }
    }

    private fun populateFields() {
        binding.editDescription.setText(task.description)

        // Se necessário, configurar outros campos para edição
    }

    private fun validateData() {
        val description = binding.editDescription.text.toString().trim()

        if (description.isNotEmpty()) {
            hideKeyboard()
            binding.progressBar.isVisible = true

            task.description = description

            saveTask()
        } else {
            showBottomSheet(message = R.string.text_description_empty_form_task_fragment)
        }
    }

    private fun saveTask() {
        if (newTask) {
            task.id = FirebaseHelper.getDatabase()
                .child("task")
                .child(FirebaseHelper.getIdUser() ?: "")
                .push().key ?: ""

            FirebaseHelper.getDatabase()
                .child("task")
                .child(FirebaseHelper.getIdUser() ?: "")
                .child(task.id)
                .setValue(task)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().popBackStack()
                        Toast.makeText(
                            requireContext(),
                            R.string.text_save_task_sucess_form_task_fragment,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            R.string.text_erro_save_task_form_task_fragment,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        R.string.text_erro_save_task_form_task_fragment,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            FirebaseHelper.getDatabase()
                .child("task")
                .child(FirebaseHelper.getIdUser() ?: "")
                .child(task.id ?: "")
                .setValue(task)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            R.string.text_update_task_sucess_form_task_fragment,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.progressBar.isVisible = false
                        Toast.makeText(
                            requireContext(),
                            R.string.text_erro_save_task_form_task_fragment,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        R.string.text_erro_save_task_form_task_fragment,
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun hideKeyboard() {
        // Implementar a funcionalidade de ocultar o teclado
    }

    private fun showBottomSheet(message: Int) {
        // Implementar a funcionalidade de exibir o bottom sheet
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }
}
