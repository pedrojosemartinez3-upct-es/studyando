package com.studyando.ui.pomodoro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studyando.databinding.FragmentPomodoroBinding
import java.util.concurrent.TimeUnit

class PomodoroFragment : Fragment() {

    private var _binding: FragmentPomodoroBinding? = null
    private val binding get() = _binding!!

    private var timer: CountDownTimer? = null
    private var isRunning = false

    private var studyMin = 25
    private var breakMin = 5
    private var totalCycles = 4
    private var currentCycle = 1
    private var inStudy = true
    private var remainingMs = 0L

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPomodoroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos pickers
        binding.pickerStudy.minValue = 5;  binding.pickerStudy.maxValue = 120; binding.pickerStudy.value = studyMin
        binding.pickerBreak.minValue = 1;  binding.pickerBreak.maxValue = 60;  binding.pickerBreak.value = breakMin
        binding.pickerCycles.minValue = 1; binding.pickerCycles.maxValue = 12; binding.pickerCycles.value = totalCycles

        // Estado inicial
        setPhase(study = true, freshCycle = true)

        binding.btnStartPause.setOnClickListener {
            if (isRunning) pauseTimer() else startTimer()
        }
        binding.btnReset.setOnClickListener {
            pauseTimer()
            // lee valores actuales
            studyMin = binding.pickerStudy.value
            breakMin = binding.pickerBreak.value
            totalCycles = binding.pickerCycles.value
            currentCycle = 1
            setPhase(study = true, freshCycle = true)
        }
    }

    private fun startTimer() {
        if (remainingMs <= 0L) remainingMs = if (inStudy) studyMin * 60_000L else breakMin * 60_000L

        timer?.cancel()
        timer = object : CountDownTimer(remainingMs, 1_000L) {
            override fun onTick(ms: Long) {
                remainingMs = ms
                renderTime(ms)
            }

            override fun onFinish() {
                remainingMs = 0L
                if (inStudy) {
                    // pasó de estudio a descanso
                    setPhase(study = false)
                    startTimer() // encadena automáticamente
                } else {
                    // terminó un ciclo
                    if (currentCycle < totalCycles) {
                        currentCycle++
                        setPhase(study = true)
                        startTimer()
                    } else {
                        // todo completado
                        isRunning = false
                        binding.btnStartPause.text = "Iniciar"
                        binding.txtPhase.text = "Completado ✅"
                        renderTime(0L)
                    }
                }
            }
        }.start()

        isRunning = true
        binding.btnStartPause.text = "Pausar"
    }

    private fun pauseTimer() {
        timer?.cancel()
        isRunning = false
        binding.btnStartPause.text = "Iniciar"
    }

    private fun setPhase(study: Boolean, freshCycle: Boolean = false) {
        inStudy = study
        remainingMs = if (study) studyMin * 60_000L else breakMin * 60_000L
        val phase = if (study) "Estudio" else "Descanso"
        val cycleInfo = "Ciclo $currentCycle de $totalCycles"
        binding.txtPhase.text = if (study) "$phase • $cycleInfo" else "$phase 💤 • $cycleInfo"
        renderTime(remainingMs)
        if (freshCycle) binding.btnStartPause.text = "Iniciar"
    }

    private fun renderTime(ms: Long) {
        val m = TimeUnit.MILLISECONDS.toMinutes(ms)
        val s = TimeUnit.MILLISECONDS.toSeconds(ms) % 60
        binding.txtTime.text = String.format("%02d:%02d", m, s)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.cancel()
        _binding = null
    }
}
