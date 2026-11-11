package com.studyando.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.studyando.R
import com.studyando.data.local.entity.TaskEntity
import com.studyando.databinding.FragmentTasksBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks), TasksAdapter.OnTaskClickListener {
    private var _b: FragmentTasksBinding? = null
    private val b get() = _b!!
    private val vm: TasksViewModel by viewModels()
    private lateinit var adapter: TasksAdapter

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        _b = FragmentTasksBinding.bind(v)

        adapter = TasksAdapter(this)
        b.notesRecyclerView.adapter = adapter
        b.notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Swipe simple para borrar
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(r: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val task = adapter.currentList[vh.bindingAdapterPosition]
                vm.deleteTask(task)
                Snackbar.make(b.root, "Tarea eliminada", Snackbar.LENGTH_LONG)
                    .setAction("DESHACER") { vm.addTask(task.title, task.description, task.priority, task.dueAt) }
                    .show()
            }
        }).attachToRecyclerView(b.notesRecyclerView)

        b.addButton.setOnClickListener { findNavController().navigate(R.id.action_tasksFragment_to_addEditTaskFragment) }
        b.btnViewCompleted.setOnClickListener { findNavController().navigate(R.id.completedFragment) }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.openTasks.collect { list ->
                adapter.submitList(list)
                b.emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onTaskClicked(task: TaskEntity) {
        val args = Bundle().apply {
            putLong("task_id", task.id)
            putString("title", task.title)
            putString("desc", task.description)
            putLong("due_at", task.dueAt ?: -1L)
            putInt("priority", task.priority)
            putBoolean("is_done", task.isDone)
        }
        findNavController().navigate(R.id.action_tasksFragment_to_addEditTaskFragment, args)
    }

    override fun onTaskCheckedChanged(task: TaskEntity, isChecked: Boolean) = vm.onTaskDoneToggled(task)
    override fun onDestroyView() { _b = null; super.onDestroyView() }
}