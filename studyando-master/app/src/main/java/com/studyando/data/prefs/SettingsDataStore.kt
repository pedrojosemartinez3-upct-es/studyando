package com.studyando.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private const val PREFS_NAME = "studyando_settings"
val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

object Keys {
    val POMO_LEN = longPreferencesKey("pomo_len_ms")
    val SHORT_BREAK = longPreferencesKey("short_break_ms")
    val LONG_BREAK = longPreferencesKey("long_break_ms")
    val TOTAL_CYCLES = intPreferencesKey("total_cycles")
}

data class PomodoroSettings(
    val pomodoroLenMs: Long = 1500000L,
    val shortBreakMs: Long = 300000L,
    val longBreakMs: Long = 900000L,
    val totalCycles: Int = 4
)

class SettingsDataStore(private val ctx: Context) {

    val pomodoroSettings: Flow<PomodoroSettings> = ctx.dataStore.data.map { p ->
        PomodoroSettings(
            pomodoroLenMs = p[Keys.POMO_LEN] ?: 1500000L,
            shortBreakMs = p[Keys.SHORT_BREAK] ?: 300000L,
            longBreakMs = p[Keys.LONG_BREAK] ?: 900000L,
            totalCycles = p[Keys.TOTAL_CYCLES] ?: 4
        )
    }

    suspend fun updatePomodoro(len: Int, short: Int, long: Int, cycles: Int) {
        ctx.dataStore.edit { p ->
            p[Keys.POMO_LEN] = len * 60000L
            p[Keys.SHORT_BREAK] = short * 60000L
            p[Keys.LONG_BREAK] = long * 60000L
            p[Keys.TOTAL_CYCLES] = cycles
        }
    }
}
