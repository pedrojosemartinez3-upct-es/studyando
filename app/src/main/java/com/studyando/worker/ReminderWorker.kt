package com.studyando.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.studyando.R

class ReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Estudio"
        val message = inputData.getString("message") ?: "Tu clase comienza pronto"

        showNotification(title, message)
        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        // CORRECCIÓN DE PERMISO: Verificación explícita para evitar SecurityException
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val builder = NotificationCompat.Builder(applicationContext, "studyando_channel")
            .setSmallIcon(R.drawable.outline_add_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        // CORRECCIÓN: System.currentTimeMillis() completo
        NotificationManagerCompat.from(applicationContext).notify(
            System.currentTimeMillis().toInt(),
            builder.build()
        )
    }
}