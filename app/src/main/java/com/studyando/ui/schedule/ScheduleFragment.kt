package com.studyando.ui.schedule

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.studyando.data.local.entity.ScheduleEntity
import com.studyando.R
import com.studyando.databinding.FragmentScheduleBinding
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScheduleFragment : Fragment(R.layout.fragment_schedule) {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScheduleViewModel by viewModels()
    private lateinit var adapter: ScheduleAdapter

    private var fetchJob: Job? = null
    private var selectedDay = 1
    private var tStart = 8 * 60 + 30
    private var tEnd = 9 * 60 + 30

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentScheduleBinding.bind(view)

        initList()
        initChips()

        binding.fabAddClass.setOnClickListener { showClassDialog() }
    }

    private fun initList() {
        adapter = ScheduleAdapter(mutableListOf()) { item -> showClassDialog(item) }
        binding.rvClasses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvClasses.adapter = adapter

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(r: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.adapterPosition
                val item = adapter.getItem(pos)

                AlertDialog.Builder(requireContext())
                    .setTitle("Borrar clase")
                    .setMessage("¿Eliminar ${item.subject}?")
                    .setPositiveButton("Sí") { _, _ ->
                        viewModel.deleteClass(item)
                        Toast.makeText(requireContext(), "Borrado", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("No") { _, _ -> adapter.notifyItemChanged(pos) }
                    .show()
            }
        }).attachToRecyclerView(binding.rvClasses)
    }

    private fun initChips() {
        binding.chipGroupDays.setOnCheckedStateChangeListener { _, ids ->
            if (ids.isNotEmpty()) {
                selectedDay = when (ids.first()) {
                    R.id.chipMon -> 1
                    R.id.chipTue -> 2
                    R.id.chipWed -> 3
                    R.id.chipThu -> 4
                    R.id.chipFri -> 5
                    R.id.chipSat -> 6
                    R.id.chipSun -> 7
                    else -> 1
                }
                refreshData(selectedDay)
            }
        }
        binding.chipMon.isChecked = true
        refreshData(1)
    }

    private fun refreshData(day: Int) {
        fetchJob?.cancel()
        fetchJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getClassesByDay(day).collect { list ->
                adapter.submit(list)
                val empty = list.isEmpty()
                binding.emptyView.visibility = if (empty) View.VISIBLE else View.GONE
                binding.rvClasses.visibility = if (empty) View.GONE else View.VISIBLE
            }
        }
    }

    private fun showClassDialog(item: ScheduleEntity? = null) {
        val root = layoutInflater.inflate(R.layout.dialog_add_class, null)
        val dialog = AlertDialog.Builder(requireContext()).setView(root).create()

        val etSub = root.findViewById<TextInputEditText>(R.id.etSubject)
        val etLoc = root.findViewById<TextInputEditText>(R.id.etLocation)
        val btnIn = root.findViewById<Button>(R.id.btnStartTime)
        val btnOut = root.findViewById<Button>(R.id.btnEndTime)
        val colorsBox = root.findViewById<LinearLayout>(R.id.colorPickerContainer)

        var activeColor = item?.color ?: 0xFFD1C4E9.toInt()
        val palette = listOf(0xFFD1C4E9.toInt(), 0xFFBBDEFB.toInt(), 0xFFC8E6C9.toInt(), 0xFFFFF9C4.toInt(), 0xFFFFCCBC.toInt())

        palette.forEach { c ->
            val v = View(requireContext()).apply {
                val px = (34 * resources.displayMetrics.density).toInt()
                layoutParams = LinearLayout.LayoutParams(px, px).apply { setMargins(10, 10, 10, 10) }
                background = ContextCompat.getDrawable(requireContext(), R.drawable.color_circle)
                backgroundTintList = ColorStateList.valueOf(c)
                alpha = if (c == activeColor) 1.0f else 0.6f

                setOnClickListener {
                    activeColor = c
                    colorsBox.children.forEach { it.alpha = 0.6f }
                    alpha = 1.0f
                }
            }
            colorsBox.addView(v)
        }

        item?.let {
            etSub.setText(it.subject)
            etLoc.setText(it.location)
            tStart = it.startMinutes
            tEnd = it.endMinutes
            btnIn.text = "Desde: ${fmt(tStart)}"
            btnOut.text = "Hasta: ${fmt(tEnd)}"
        }

        btnIn.setOnClickListener {
            pickTime(tStart) { h, m ->
                tStart = h * 60 + m
                btnIn.text = "Desde: ${fmt(tStart)}"
            }
        }

        btnOut.setOnClickListener {
            pickTime(tEnd) { h, m ->
                tEnd = h * 60 + m
                btnOut.text = "Hasta: ${fmt(tEnd)}"
            }
        }

        root.findViewById<Button>(R.id.btnSaveClass).setOnClickListener {
            val name = etSub.text.toString().trim()
            if (name.isEmpty()) {
                etSub.error = "Falta nombre"
                return@setOnClickListener
            }

            if (item == null) {
                viewModel.addClass(name, etLoc.text.toString(), selectedDay, tStart, tEnd, activeColor)
            } else {
                viewModel.updateClass(item.copy(
                    subject = name,
                    location = etLoc.text.toString(),
                    startMinutes = tStart,
                    endMinutes = tEnd,
                    color = activeColor
                ))
            }
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun pickTime(initMins: Int, onTime: (Int, Int) -> Unit) {
        TimePickerDialog(requireContext(), { _, h, m -> onTime(h, m) }, initMins / 60, initMins % 60, true).show()
    }

    private fun fmt(m: Int) = String.format("%02d:%02d", m / 60, m % 60)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}