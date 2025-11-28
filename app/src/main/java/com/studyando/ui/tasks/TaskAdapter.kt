package com.studyando.ui.tasks // Asegúrate de que esté en este paquete

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.studyando.R
import com.studyando.data.local.entity.TaskEntity
import com.studyando.databinding.NoteItemBinding // Reutilizamos tu layout de item
import java.text.SimpleDateFormat
import java.util.*

class TasksAdapter(
    private val listener: OnTaskClickListener // La interfaz que implementa el Fragmento
) : ListAdapter<TaskEntity, TasksAdapter.TaskViewHolder>(TaskDiffCallback()) {

    private val dateFmt = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

    inner class TaskViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            // Click en el CheckBox
            binding.checkDone.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    listener.onTaskCheckedChanged(task, binding.checkDone.isChecked)
                }
            }

            // Click en el item (para editar)
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    listener.onTaskClicked(task)
                }
            }
        }

        fun bind(task: TaskEntity) {
            binding.titleTextView.text = task.title
            binding.contentTextView.text = task.description

            // Lógica de la fecha de entrega
            if (task.dueAt != null) {
                binding.dueTextView.visibility = View.VISIBLE
                binding.dueTextView.text = "Entrega: ${dateFmt.format(Date(task.dueAt))}"
                val vencida = !task.isDone && task.dueAt < System.currentTimeMillis()
                binding.dueTextView.setTextColor(
                    if (vencida) ContextCompat.getColor(itemView.context, android.R.color.holo_red_dark)
                    else ContextCompat.getColor(itemView.context, android.R.color.darker_gray)
                )
            } else {
                binding.dueTextView.visibility = View.GONE
            }

            // Lógica de Tarea Completada (Tachado)
            binding.checkDone.isChecked = task.isDone
            applyCompletedStyle(binding, task.isDone)

            binding.optionsButton.visibility = View.GONE // Ocultamos el menú de 3 puntos por ahora
        }

        private fun applyCompletedStyle(binding: NoteItemBinding, completed: Boolean) {
            if (completed) {
                binding.titleTextView.paintFlags = binding.titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.contentTextView.paintFlags = binding.contentTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.titleTextView.paintFlags = binding.titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                binding.contentTextView.paintFlags = binding.contentTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = getItem(position)
        holder.bind(currentTask)
    }

    // --- DiffUtil (para eficiencia) ---
    class TaskDiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity) =
            oldItem == newItem
    }

    // --- ESTA ES LA INTERFAZ QUE FALTABA ---
    interface OnTaskClickListener {
        fun onTaskClicked(task: TaskEntity) // Para editar
        fun onTaskCheckedChanged(task: TaskEntity, isChecked: Boolean) // Para completar
    }
}