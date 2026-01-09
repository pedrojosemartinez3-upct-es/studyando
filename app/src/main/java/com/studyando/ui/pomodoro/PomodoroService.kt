package com.studyando.ui.pomodoro

import android.app.*
import android.content.Intent
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.studyando.R
import kotlinx.coroutines.flow.MutableStateFlow

class PomodoroService : Service() {

    companion object {
        val timeRemaining = MutableStateFlow(0L)
        val isRunning = MutableStateFlow(false)
        val currentPhase = MutableStateFlow("Listo")
        val progress = MutableStateFlow(100)

        const val ACTION_START = "START"
        const val ACTION_PAUSE = "PAUSE"
        const val ACTION_RESET = "RESET"

        private const val NOTIFICATION_ID = 1
        private const val ALERT_ID = 2
    }

    private var timer: CountDownTimer? = null
    private val CHANNEL_ID = "PomoChannel"
    private val ALERT_CHANNEL_ID = "PomoAlerts"

    // Generador de pitidos del sistema
    private var toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)

    private var sMin = 25
    private var bMin = 5
    private var totalCycles = 4
    private var currentCycle = 1
    private var isStudyPhase = true

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                sMin = intent.getIntExtra("sMin", 25)
                bMin = intent.getIntExtra("bMin", 5)
                totalCycles = intent.getIntExtra("totalC", 4)

                val duration = if (timeRemaining.value > 0) timeRemaining.value else (sMin * 60000L)
                startTimer(duration)
            }
            ACTION_PAUSE -> pauseTimer()
            ACTION_RESET -> resetTimer()
        }
        return START_NOT_STICKY
    }

    private fun startTimer(duration: Long) {
        isRunning.value = true
        setupChannels()
        startForeground(NOTIFICATION_ID, buildNotify("Ejecutando..."))

        timer?.cancel()
        timer = object : CountDownTimer(duration, 1000) {
            override fun onTick(ms: Long) {
                timeRemaining.value = ms
                val total = if (isStudyPhase) sMin * 60000L else bMin * 60000L
                progress.value = ((ms.toFloat() / total) * 100).toInt()

                val timeStr = String.format("%02d:%02d", ms/60000, (ms/1000)%60)
                updateUIState(timeStr)
                updateNotification(timeStr)
            }

            override fun onFinish() {
                handlePhaseTransition()
            }
        }.start()
    }

    private fun handlePhaseTransition() {
        if (isStudyPhase) {
            // TERMINA ESTUDIO -> EMPIEZA DESCANSO
            playAlert(isEndOfStudy = true)
            isStudyPhase = false
            startTimer(bMin * 60000L)
        } else {
            if (currentCycle < totalCycles) {
                // TERMINA DESCANSO -> EMPIEZA NUEVO CICLO
                currentCycle++
                isStudyPhase = true
                playAlert(isEndOfStudy = false)
                startTimer(sMin * 60000L)
            } else {
                currentPhase.value = "¡Sesión finalizada!"
                playAlert(isEndOfStudy = false) // Pitido final
                resetTimer()
            }
        }
    }

    private fun playAlert(isEndOfStudy: Boolean) {
        // 1. PITIDOS DIFERENTES
        if (isEndOfStudy) {
            // Doble pitido corto para "A descansar"
            toneGenerator.startTone(ToneGenerator.TONE_CDMA_PIP, 500)
        } else {
            // Pitido largo para "A estudiar / Nuevo ciclo"
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 800)
        }

        // 2. VIBRACIÓN
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(800, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(800)
        }

        // 3. NOTIFICACIÓN QUE SALTA (Heads-up)
        val title = if (isEndOfStudy) "¡Hora de descansar!" else "¡A estudiar!"
        val text = if (isEndOfStudy) "Has terminado tu bloque. Tómate un respiro." else "Empezamos el ciclo $currentCycle."

        val alertNotify = NotificationCompat.Builder(this, ALERT_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(ALERT_ID, alertNotify)
    }

    private fun pauseTimer() {
        timer?.cancel()
        isRunning.value = false
        stopForeground(STOP_FOREGROUND_DETACH)
    }

    private fun resetTimer() {
        timer?.cancel()
        isRunning.value = false
        timeRemaining.value = 0
        currentCycle = 1
        isStudyPhase = true
        currentPhase.value = "Listo"
        progress.value = 100
        stopSelf()
    }

    private fun updateUIState(time: String) {
        val phase = if (isStudyPhase) "Estudio" else "Descanso"
        currentPhase.value = "$phase • Ciclo $currentCycle de $totalCycles"
    }

    private fun buildNotify(timeStr: String): Notification {
        val phase = if (isStudyPhase) "Estudio" else "Descanso"
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Studyando")
            .setContentText("$phase: $timeStr (Ciclo $currentCycle/$totalCycles)")
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }

    private fun updateNotification(time: String) {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, buildNotify(time))
    }

    private fun setupChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = getSystemService(NotificationManager::class.java)
            val mainChan = NotificationChannel(CHANNEL_ID, "Cronómetro", NotificationManager.IMPORTANCE_LOW)
            val alertChan = NotificationChannel(ALERT_CHANNEL_ID, "Alertas de Fase", NotificationManager.IMPORTANCE_HIGH).apply {
                enableVibration(true)
                description = "Notificaciones de fin de sesión y descanso"
            }
            manager.createNotificationChannel(mainChan)
            manager.createNotificationChannel(alertChan)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
    override fun onDestroy() {
        timer?.cancel()
        toneGenerator.release()
        super.onDestroy()
    }
}