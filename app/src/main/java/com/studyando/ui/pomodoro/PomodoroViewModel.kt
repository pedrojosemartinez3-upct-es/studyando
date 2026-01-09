package com.studyando.ui.pomodoro

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.studyando.data.prefs.SettingsDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PomodoroViewModel @Inject constructor(
    application: Application,
    private val settings: SettingsDataStore
) : AndroidViewModel(application) {

    val timeStr: LiveData<String> = PomodoroService.timeRemaining.asLiveData().map { ms: Long ->
        if (ms == 0L) {
            "${sMin}:00"
        } else {
            val minutes = ms / 60000
            val seconds = (ms / 1000) % 60
            String.format("%02d:%02d", minutes, seconds)
        }
    }

    val progress: LiveData<Int> = PomodoroService.progress.asLiveData()
    val phaseStr: LiveData<String> = PomodoroService.currentPhase.asLiveData()
    val isRunning: LiveData<Boolean> = PomodoroService.isRunning.asLiveData()

    var sMin = 25
    var bMin = 5
    var totalC = 4

    fun toggle() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, PomodoroService::class.java)

        if (isRunning.value == true) {
            intent.action = PomodoroService.ACTION_PAUSE
        } else {
            intent.action = PomodoroService.ACTION_START
            intent.putExtra("sMin", sMin)
            intent.putExtra("bMin", bMin)
            intent.putExtra("totalC", totalC)
        }
        context.startService(intent)
    }

    fun reset() {
        val context = getApplication<Application>().applicationContext
        val intent = Intent(context, PomodoroService::class.java).apply {
            action = PomodoroService.ACTION_RESET
        }
        context.startService(intent)
    }
}