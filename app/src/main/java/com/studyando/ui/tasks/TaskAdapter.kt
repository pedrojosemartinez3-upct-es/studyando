package com.studyando.ui.tasks

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
import com.studyando.databinding.NoteItemBinding
import java.text.SimpleDateFormat
import java.util.*

class TasksAdapter(private val listener: OnTaskClickListener) :
    ListAdapter<TaskEntity, TasksAdapter.VH>(DiffCallback) {

    private val df = SimpleDateFormat("dd MMM", Locale.getDefault())

    inner class VH(val b: NoteItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(item: TaskEntity) {
            b.titleTextView.text = item.title
            b.contentTextView.text = item.description
            b.checkDone.isChecked = item.isDone

            val color = when (item.priority) {
                1 -> R.color.md_theme_light_primary
                2 -> android.R.color.holo_orange_light
                else -> R.color.md_theme_light_surfaceVariant
            }
            b.colorStrip.setBackgroundColor(ContextCompat.getColor(itemView.context, color))

            if (item.dueAt != null) {
                b.dateContainer.visibility = View.VISIBLE
                b.dueTextView.text = "Entrega: ${df.format(Date(item.dueAt))}"

                val overdue = !item.isDone && item.dueAt < System.currentTimeMillis()
                b.dateContainer.setBackgroundResource(if (overdue) R.drawable.bg_pill_info_red else R.drawable.bg_pill_info)
            } else {
                b.dateContainer.visibility = View.GONE
            }

            val flag = if (item.isDone) Paint.STRIKE_THRU_TEXT_FLAG else 0
            b.titleTextView.paintFlags = flag
            b.root.alpha = if (item.isDone) 0.6f else 1.0f
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, t: Int) =
        VH(NoteItemBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(holder: VH, pos: Int) {
        val item = getItem(pos)
        holder.bind(item)
        holder.b.checkDone.setOnClickListener { listener.onTaskCheckedChanged(item, holder.b.checkDone.isChecked) }
        holder.itemView.setOnClickListener { listener.onTaskClicked(item) }
    }

    object DiffCallback : DiffUtil.ItemCallback<TaskEntity>() {
        override fun areItemsTheSame(old: TaskEntity, new: TaskEntity) = old.id == new.id
        override fun areContentsTheSame(old: TaskEntity, new: TaskEntity) = old == new
    }

    interface OnTaskClickListener {
        fun onTaskClicked(task: TaskEntity)
        fun onTaskCheckedChanged(task: TaskEntity, isChecked: Boolean)
    }
}