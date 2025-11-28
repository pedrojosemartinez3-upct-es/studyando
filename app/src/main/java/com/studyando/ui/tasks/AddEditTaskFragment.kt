package com.studyando.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels // ¡Importante!
import com.studyando.R
import com.studyando.databinding.DialogAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : DialogFragment(R.layout.dialog_add_task) {

    // --- ESTA ES LA LÍNEA CORREGIDA ---
    // Usamos 'activityViewModels' para compartir el ViewModel con la Activity
    private val viewModel: TasksViewModel by activityViewModels()

    private var _binding: DialogAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DialogAddTaskBinding.bind(view)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            if (title.isEmpty()) {
                Toast.makeText(requireContext(), "El título no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ahora 'addTask' SÍ se encontrará en el ViewModel
            viewModel.addTask(title, description, 2, null)
            dismiss() // Cierra el diálogo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}