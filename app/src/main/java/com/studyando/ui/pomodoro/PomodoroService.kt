package com.studyando.ui.pomodoro

import android.app.*
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.studyando.R

class PomodoroService : Service() {
    private var timer: CountDownTimer? = null
    private val CID = "PomoChannel"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val ms = intent?.getLongExtra("duration", 0L) ?: 0L
        setupChannel()
        startForeground(1, buildNotify("En curso..."))

        timer?.cancel()
        timer = object : CountDownTimer(ms, 1000) {
            override fun onTick(rem: Long) = updateNotify(String.format("%02d:%02d", rem/60000, (rem/1000)%60))
            override fun onFinish() = stopSelf()
        }.start()

        return START_NOT_STICKY
    }

    private fun buildNotify(txt: String) = NotificationCompat.Builder(this, CID)
        .setContentTitle("Studyando Pomodoro")
        .setContentText(txt)
        .setSmallIcon(R.drawable.baseline_alarm_24)
        .setOngoing(true)
        .build()

    private fun updateNotify(time: String) =
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).notify(1, buildNotify("Tiempo: $time"))

    private fun setupChannel() {
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            val chan = NotificationChannel(CID, "Pomodoro", NotificationManager.IMPORTANCE_LOW)
            (getSystemService(NotificationManager::class.java)).createNotificationChannel(chan)
        }
    }

    override fun onBind(i: Intent?): IBinder? = null
    override fun onDestroy() { timer?.cancel(); super.onDestroy() }
}