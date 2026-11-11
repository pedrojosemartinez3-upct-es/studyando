package com.studyando.ui.pomodoro

import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.studyando.R
import com.studyando.databinding.FragmentPomodoroBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PomodoroFragment : Fragment(R.layout.fragment_pomodoro) {
    private var _b: FragmentPomodoroBinding? = null
    private val b get() = _b!!

    private val vm: PomodoroViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentPomodoroBinding.bind(view)

        // Suscripción a datos del ViewModel
        vm.timeStr.observe(viewLifecycleOwner) { b.txtTime.text = it }
        vm.progress.observe(viewLifecycleOwner) { b.progressTimer.setProgress(it, true) }
        vm.phaseStr.observe(viewLifecycleOwner) { b.txtPhase.text = it }
        vm.isRunning.observe(viewLifecycleOwner) { running ->
            b.btnStartPause.text = if (running) "Pausar" else "Iniciar"
            // Deshabilitar sliders mientras corre para evitar inconsistencias
            b.sliderStudy.isEnabled = !running
            b.sliderBreak.isEnabled = !running
            b.sliderCycles.isEnabled = !running
        }

        // Configuración de Sliders
        b.sliderStudy.apply {
            value = vm.sMin.toFloat()
            addOnChangeListener { _, v, _ -> vm.sMin = v.toInt() }
        }

        b.sliderBreak.apply {
            value = vm.bMin.toFloat()
            addOnChangeListener { _, v, _ -> vm.bMin = v.toInt() }
        }

        b.sliderCycles.apply {
            value = vm.totalC.toFloat()
            addOnChangeListener { _, v, _ -> vm.totalC = v.toInt() }
        }

        b.btnStartPause.setOnClickListener { vm.toggle() }
        b.btnReset.setOnClickListener { vm.reset() }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}