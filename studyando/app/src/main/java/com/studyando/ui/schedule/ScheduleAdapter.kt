package com.studyando.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studyando.data.local.entity.ScheduleEntity
import com.studyando.databinding.ItemClassBinding
import java.util.Locale

class ScheduleAdapter(
    private var items: MutableList<ScheduleEntity>,
    private val onClassClick: (ScheduleEntity) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.VH>() {

    class VH(val b: ItemClassBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        with(holder.b) {
            txtSubject.text = item.subject
            txtInfo.text = "${formatTime(item.startMinutes)}–${formatTime(item.endMinutes)}" +
                    (if (!item.location.isNullOrBlank()) " • ${item.location}" else "")
            root.setCardBackgroundColor(item.color)
        }
        holder.itemView.setOnClickListener { onClassClick(item) }
    }

    fun getItem(position: Int) = items[position]

    fun submit(list: List<ScheduleEntity>) {
        items.clear(); items.addAll(list); notifyDataSetChanged()
    }
    private fun formatTime(m: Int) = String.format(Locale.getDefault(), "%02d:%02d", m / 60, m % 60)

}