package com.studyando.ui.pomodoro

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PomodoroViewModel : ViewModel() {
    val timeStr = MutableLiveData("25:00")
    val progress = MutableLiveData(100)
    val phaseStr = MutableLiveData("Listo")
    val isRunning = MutableLiveData(false)
    val alarmEvent = MutableLiveData<Boolean?>()

    var sMin = 25
    var bMin = 5
    var totalC = 4
    var curC = 1
    var isStudy = true
    var remMs = 25 * 60000L

    private var timer: CountDownTimer? = null

    fun toggle() { if (isRunning.value == true) pause() else start() }

    fun start() {
        isRunning.value = true
        timer?.cancel()
        timer = object : CountDownTimer(remMs, 1000L) {
            override fun onTick(t: Long) {
                remMs = t
                val total = if (isStudy) sMin * 60000L else bMin * 60000L
                progress.value = ((t.toFloat() / total) * 100).toInt()
                updateUI(t)
            }
            override fun onFinish() {
                isRunning.value = false
                handleTransition()
            }
        }.start()
    }

    private fun handleTransition() {
        alarmEvent.value = true // Disparar sonido

        if (isStudy) {
            // Fin de estudio -> Empieza descanso
            isStudy = false
            remMs = bMin * 60000L
            updateUI(remMs) // Forzar texto de descanso inmediatamente
            start()
        } else {
            // Fin de descanso -> ¿Siguiente ciclo?
            if (curC < totalC) {
                curC++
                isStudy = true
                remMs = sMin * 60000L
                updateUI(remMs)
                start()
            } else {
                // Sesión terminada al completo
                reset()
                phaseStr.value = "¡Sesión finalizada!"
            }
        }
    }

    fun pause() { timer?.cancel(); isRunning.value = false }

    fun reset() {
        pause()
        curC = 1
        isStudy = true
        remMs = sMin * 60000L
        updateUI(remMs)
    }

    private fun updateUI(ms: Long) {
        val min = ms / 60000
        val sec = (ms / 1000) % 60
        timeStr.value = String.format("%02d:%02d", min, sec)
        val phase = if (isStudy) "Estudio" else "Descanso 💤"
        phaseStr.value = "$phase • Ciclo $curC de $totalC"
    }

    override fun onCleared() { timer?.cancel(); super.onCleared() }
}