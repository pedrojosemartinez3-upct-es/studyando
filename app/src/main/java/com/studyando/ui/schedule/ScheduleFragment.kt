package com.studyando.ui.schedule

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.studyando.data.local.entity.ScheduleEntity // ¡Importar!
import com.studyando.databinding.FragmentScheduleBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint // ¡MUY IMPORTANTE!
class ScheduleFragment : Fragment() {

    private var _b: FragmentScheduleBinding? = null
    private val b get() = _b!!

    // Inyecta el ViewModel
    private val viewModel: ScheduleViewModel by viewModels()

    private lateinit var adapter: ScheduleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentScheduleBinding.inflate(layoutInflater)

        // Tu adapter está bien, pero debe aceptar la nueva Entity
        adapter = ScheduleAdapter(mutableListOf())

        b.rvClasses.layoutManager = LinearLayoutManager(requireContext())
        b.rvClasses.adapter = adapter

        val days = arrayOf("Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo")
        b.spDay.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, days)

        b.spDay.setOnItemSelectedListenerCompat { _, _, pos, _ ->
            loadDay(pos + 1)
        }

        b.fabAddClass.setOnClickListener { showAddDialog() }

        loadDay(1) // Lunes por defecto
    }

    private fun loadDay(day: Int) {
        // Observamos el Flow del ViewModel
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.getClassesByDay(day).collect { sessions ->
                adapter.submit(sessions) // El adapter recibe la lista
            }
        }
    }

    private fun showAddDialog() {
        // Tu lógica del diálogo está bien, solo cambia el guardado
        val ctx = requireContext()
        val container = LinearLayout(ctx).apply { /*...*/ }
        val inputSubject = EditText(ctx).apply { hint = "Asignatura" }
        val inputLocation = EditText(ctx).apply { hint = "Aula (opcional)" }
        container.addView(inputSubject)
        container.addView(inputLocation)

        var start = 8*60
        var end = 9*60

        AlertDialog.Builder(ctx)
            .setTitle("Nueva clase")
            .setView(container)
            .setPositiveButton("Elegir horas") { _, _ ->
                TimePickerDialog(ctx, { _, h, m ->
                    start = h*60 + m
                    TimePickerDialog(ctx, { _, h2, m2 ->
                        end = h2*60 + m2
                        val day = b.spDay.selectedItemPosition + 1

                        // ¡Usa el ViewModel para guardar!
                        viewModel.addClassSession(
                            subject = inputSubject.text.toString().trim(),
                            location = inputLocation.text.toString().trim().ifBlank { null },
                            day = day,
                            start = start,
                            end = end
                        )
                        // Ya no necesitas loadDay(day), el Flow lo hará automático

                    }, end/60, end%60, true).show()
                }, start/60, start%60, true).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}

/** Extension sencilla para evitar boilerplate en Spinner */
private inline fun android.widget.Spinner.setOnItemSelectedListenerCompat(crossinline f: (parent: android.widget.AdapterView<*>, view: View?, position: Int, id: Long) -> Unit) {
    onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p: android.widget.AdapterView<*>, v: View?, pos: Int, id: Long) = f(p, v, pos, id)
        override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
    }
}