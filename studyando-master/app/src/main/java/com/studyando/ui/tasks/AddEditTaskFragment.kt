package com.studyando.ui.tasks

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
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

        // Se usa -1L para asegurar que la comparación sea entre tipos Long
        val id = arguments?.getLong("taskId", -1L) ?: -1L

        if (id != -1L) {
            b.etTitle.setText(arguments?.getString("title"))
            b.etDescription.setText(arguments?.getString("desc"))

            // Se especifica -1L como valor por defecto para el Long
            arguments?.getLong("due_at", -1L)?.takeIf { it != -1L }?.let {
                due = it
                b.btnPickDate.text = "Entrega: ${fmtDate(it)}"
            }
            b.btnSave.text = "Actualizar"
        }

        b.btnPickDate.setOnClickListener { showPicker() }
        b.btnSave.setOnClickListener { save(id) }
    }

    private fun save(id: Long) {
        val t = b.etTitle.text.toString().trim()
        if (t.isEmpty()) {
            b.etTitle.error = "Falta título"
            return
        }

        // Recuperamos los valores originales para evitar que se reseteen al editar
        val originalPriority = arguments?.getInt("priority", 2) ?: 2
        val originalIsDone = arguments?.getBoolean("is_done", false) ?: false

        if (id != -1L) {
            // Se pasan todos los parámetros a TaskEntity, incluyendo isDone como Booleano
            vm.updateTask(
                TaskEntity(
                    id = id,
                    title = t,
                    description = b.etDescription.text.toString(),
                    dueAt = due,
                    priority = originalPriority,
                    isDone = originalIsDone
                )
            )
        } else {
            // Si es una tarea nueva, se crea con los valores por defecto
            vm.addTask(t, b.etDescription.text.toString(), 2, due)
        }
        dismiss()
    }

    private fun showPicker() {
        val c = Calendar.getInstance()
        due?.let { c.timeInMillis = it }
        DatePickerDialog(requireContext(), { _, y, m, d ->
            c.set(y, m, d)
            due = c.timeInMillis
            // fmtDate devuelve String, evitando errores de tipo en la UI
            b.btnPickDate.text = "Entrega: ${fmtDate(due!!)}"
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun fmtDate(ms: Long): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(ms))
    }

    override fun onDestroyView() {
        _b = null
        super.onDestroyView()
    }
}