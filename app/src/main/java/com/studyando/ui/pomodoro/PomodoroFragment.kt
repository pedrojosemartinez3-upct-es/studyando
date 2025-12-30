package com.studyando.ui.pomodoro

import android.media.RingtoneManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.studyando.R
import com.studyando.databinding.FragmentPomodoroBinding

class PomodoroFragment : Fragment(R.layout.fragment_pomodoro) {
    private var _b: FragmentPomodoroBinding? = null
    private val b get() = _b!!

    // Compartido con la actividad para que no muera al navegar
    private val vm: PomodoroViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _b = FragmentPomodoroBinding.bind(view)

        // Observadores (Vuelven a conectar automáticamente al regresar a la pantalla)
        vm.timeStr.observe(viewLifecycleOwner) { b.txtTime.text = it }
        vm.progress.observe(viewLifecycleOwner) { b.progressTimer.setProgress(it, true) }
        vm.phaseStr.observe(viewLifecycleOwner) { b.txtPhase.text = it }
        vm.isRunning.observe(viewLifecycleOwner) { b.btnStartPause.text = if (it) "Pausar" else "Iniciar" }

        // Evento de Alarma
        vm.alarmEvent.observe(viewLifecycleOwner) {
            if (it == true) {
                playAlarm()
                vm.alarmEvent.value = null
            }
        }

        // Sliders (Vinculados al VM)
        b.sliderStudy.apply {
            value = vm.sMin.toFloat()
            addOnChangeListener { _, v, _ ->
                vm.sMin = v.toInt()
                if (vm.isRunning.value == false && vm.isStudy) vm.reset()
            }
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

    private fun playAlarm() {
        try {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            RingtoneManager.getRingtone(context, uri).play()
        } catch (e: Exception) { e.printStackTrace() }
    }

    override fun onDestroyView() { _b = null; super.onDestroyView() }
}