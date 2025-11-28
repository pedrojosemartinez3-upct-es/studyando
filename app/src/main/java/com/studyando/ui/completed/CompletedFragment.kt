package com.studyando.ui.completed

import android.os.Bundle
import com.studyando.R
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.studyando.data.local.entity.TaskEntity
import com.studyando.databinding.FragmentCompletedBinding
import com.studyando.ui.tasks.TasksAdapter // ¡Import clave!
import com.studyando.ui.tasks.TasksViewModel // ¡Import clave!
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CompletedFragment : Fragment(R.layout.fragment_completed), TasksAdapter.OnTaskClickListener {

    private var _binding: FragmentCompletedBinding? = null
    private val binding get() = _binding!!

    // Reutilizamos el TasksViewModel
    private val viewModel: TasksViewModel by viewModels()

    // El import de TasksAdapter ahora funciona
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCompletedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TasksAdapter(this) // El adapter moderno
        binding.recyclerCompleted.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerCompleted.adapter = adapter

        // Observa el Flow de tareas completadas
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.completedTasks.collect { tasks ->
                adapter.submitList(tasks)
                updateEmpty(tasks.isEmpty())
            }
        }

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
    }

    private fun updateEmpty(empty: Boolean) {
        binding.emptyView.visibility = if (empty) View.VISIBLE else View.GONE
        binding.recyclerCompleted.visibility = if (empty) View.GONE else View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            findNavController().popBackStack()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = null
    }

    // --- Implementación de Clicks (los "override" ahora funcionan) ---
    override fun onTaskClicked(task: TaskEntity) { /* No hacer nada para editar */ }

    override fun onTaskCheckedChanged(task: TaskEntity, isChecked: Boolean) {
        viewModel.onTaskDoneToggled(task) // Permite re-abrir la tarea
    }
}