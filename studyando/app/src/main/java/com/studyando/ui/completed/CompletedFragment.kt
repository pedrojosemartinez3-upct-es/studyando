package com.studyando.ui.completed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.studyando.R
import com.studyando.data.local.entity.TaskEntity
import com.studyando.databinding.FragmentCompletedBinding
import com.studyando.ui.tasks.TasksAdapter
import com.studyando.ui.tasks.TasksViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompletedFragment : Fragment(R.layout.fragment_completed), TasksAdapter.OnTaskClickListener {
    private var _b: FragmentCompletedBinding? = null
    private val b get() = _b!!
    private val vm: TasksViewModel by viewModels()
    private lateinit var adapter: TasksAdapter

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        _b = FragmentCompletedBinding.bind(v)

        adapter = TasksAdapter(this)
        b.recyclerCompleted.adapter = adapter
        b.recyclerCompleted.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        b.btnBack.setOnClickListener { findNavController().popBackStack() }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.completedTasks.collect { list ->
                adapter.submitList(list)
                val empty = list.isEmpty()
                b.emptyView.visibility = if (empty) View.VISIBLE else View.GONE
                b.recyclerCompleted.visibility = if (empty) View.GONE else View.VISIBLE
            }
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(r: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                vm.deleteTask(adapter.currentList[vh.bindingAdapterPosition])
                Snackbar.make(b.root, "Historial limpiado", Snackbar.LENGTH_LONG).show()
            }
        }).attachToRecyclerView(b.recyclerCompleted)
    }

    override fun onTaskClicked(task: TaskEntity) {}
    override fun onTaskCheckedChanged(task: TaskEntity, isChecked: Boolean) = vm.onTaskDoneToggled(task)
    override fun onDestroyView() { _b = null; super.onDestroyView() }
}