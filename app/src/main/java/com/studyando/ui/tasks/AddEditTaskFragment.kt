package com.studyando.ui.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.studyando.R
import com.studyando.data.local.entity.TaskEntity
import com.studyando.databinding.DialogAddTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
@AndroidEntryPoint
class AddEditTaskFragment : DialogFragment(R.layout.dialog_add_task) {
    private val vm: TasksViewModel by activityViewModels()
    private var _b: DialogAddTaskBinding? = null
    private val b get() = _b!!
    private var due: Long? = null

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        _b = DialogAddTaskBinding.bind(v)

        val id = arguments?.getInt("task_id", -1) ?: -1
        if (id != -1) {
            b.etTitle.setText(arguments?.getString("title"))
            b.etDescription.setText(arguments?.getString("desc"))
            arguments?.getLong("due_at", -1L)?.takeIf { it != -1L }?.let {
                due = it
                b.btnPickDate.text = "Entrega: ${fmtDate(it)}"
            }
            b.btnSave.text = "Actualizar"
        }

        b.btnPickDate.setOnClickListener { showPicker() }
        b.btnSave.setOnClickListener { save(id) }
    }

    private fun save(id: Int) {
        val t = b.etTitle.text.toString().trim()
        if (t.isEmpty()) { b.etTitle.error = "Falta título"; return }

        if (id != -1) vm.updateTask(TaskEntity(id, t, b.etDescription.text.toString(), due, 2))
        else vm.addTask(t, b.etDescription.text.toString(), 2, due)
        dismiss()
    }

    private fun showPicker() {
        val c = Calendar.getInstance()
        due?.let { c.timeInMillis = it }
        DatePickerDialog(requireContext(), { _, y, m, d ->
            c.set(y, m, d)
            due = c.timeInMillis
            b.btnPickDate.text = "Entrega: ${fmtDate(due!!)}"
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun fmtDate(ms: Long) = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(ms))
    override fun onDestroyView() { _b = null; super.onDestroyView() }
}