package com.studyando.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
// ¡Importa la nueva Entity de Room!
import com.studyando.data.local.entity.ScheduleEntity
import com.studyando.databinding.ItemClassBinding
import java.util.Locale

class ScheduleAdapter(
    // ¡Cambiado! Ahora usa ScheduleEntity
    private var items: MutableList<ScheduleEntity>
) : RecyclerView.Adapter<ScheduleAdapter.VH>() {

    class VH(val b: ItemClassBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemClassBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = items[position] // 's' ahora es un ScheduleEntity

        // El resto de tu código funciona igual porque los nombres de las
        // propiedades (subject, dayOfWeek, etc.) son los mismos.
        holder.b.txtSubject.text = s.subject
        holder.b.txtInfo.text = "${dayName(s.dayOfWeek)} • ${minsToStr(s.startMinutes)}–${minsToStr(s.endMinutes)}" +
                (if (!s.location.isNullOrBlank()) " • ${s.location}" else "")
    }

    // ¡Cambiado! Acepta la nueva lista de ScheduleEntity
    fun submit(list: List<ScheduleEntity>) {
        items.clear(); items.addAll(list); notifyDataSetChanged()
    }

    // --- Tus funciones de ayuda (están perfectas) ---
    private fun minsToStr(m: Int): String = String.format(Locale.getDefault(), "%02d:%02d", m/60, m%60)

    private fun dayName(d: Int): String = when(d){
        1->"Lunes";2->"Martes";3->"Miércoles";4->"Jueves";5->"Viernes";6->"Sábado";7->"Domingo"; else->"?"
    }
}