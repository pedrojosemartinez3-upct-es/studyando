package com.studyando.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.studyando.R
import com.studyando.data.local.entity.TaskEntity
import com.studyando.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint // ¡MUY IMPORTANTE!
class TasksFragment : Fragment(R.layout.fragment_tasks), TasksAdapter.OnTaskClickListener {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    // Inyecta el ViewModel
    private val viewModel: TasksViewModel by viewModels()

    // El import de TasksAdapter ahora funciona
    private lateinit var tasksAdapter: TasksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTasksBinding.bind(view)

        // Usamos el TasksAdapter moderno
        tasksAdapter = TasksAdapter(this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.notesRecyclerView.adapter = tasksAdapter

        // TODO: Volver a implementar el Swipe con ItemTouchHelper

        binding.addButton.setOnClickListener {
            // Esta referencia ahora funciona
            findNavController().navigate(R.id.action_tasksFragment_to_addEditTaskFragment)
        }

        // Observa el Flow del ViewModel
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.openTasks.collect { tasks ->
                tasksAdapter.submitList(tasks) // "submitList" ahora se reconoce
                updateEmptyState(tasks.isEmpty())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Ya no se necesita refreshData, el Flow lo hace automático
    }

    private fun updateEmptyState(empty: Boolean) {
        binding.emptyView.visibility = if (empty) View.VISIBLE else View.GONE
        binding.notesRecyclerView.visibility = if (empty) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // --- Implementación de los Clicks (los "override" ahora funcionan) ---
    override fun onTaskClicked(task: TaskEntity) {
        // Navegar para editar
        // val action = TasksFragmentDirections.actionTasksFragmentToAddEditTaskFragment(task.id)
        // findNavController().navigate(action)
    }

    override fun onTaskCheckedChanged(task: TaskEntity, isChecked: Boolean) {
        // Avisa al ViewModel
        viewModel.onTaskDoneToggled(task)
    }
}