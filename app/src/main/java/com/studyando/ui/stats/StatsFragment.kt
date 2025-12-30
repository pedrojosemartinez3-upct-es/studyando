package com.studyando.ui.stats

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.studyando.R
import com.studyando.databinding.FragmentStatsBinding
import com.studyando.ui.tasks.TasksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine

@AndroidEntryPoint
class StatsFragment : Fragment(R.layout.fragment_stats) {
    private var _b: FragmentStatsBinding? = null
    private val b get() = _b!!
    private val vm: TasksViewModel by viewModels()

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)
        _b = FragmentStatsBinding.bind(v)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            combine(vm.openTasks, vm.completedTasks) { open, done ->
                b.txtTotalTasks.text = (open.size + done.size).toString()
                b.txtDoneTasks.text = done.size.toString()

                val dataSet = BarDataSet(listOf(BarEntry(1f, done.size.toFloat())), "Completadas")
                dataSet.color = ContextCompat.getColor(requireContext(), R.color.md_theme_light_primary)

                b.barChart.data = BarData(dataSet)
                b.barChart.apply { animateY(800); invalidate() }
            }.collect {}
        }
    }
    override fun onDestroyView() { _b = null; super.onDestroyView() }
}