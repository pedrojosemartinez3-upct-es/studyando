package com.studyando.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private const val PREFS_NAME = "studyando_settings"
val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

object Keys {
    val POMO_LEN = longPreferencesKey("pomo_len_ms")            // por defecto 25 min
    val SHORT_BREAK = longPreferencesKey("short_break_ms")       // 5 min
    val LONG_BREAK = longPreferencesKey("long_break_ms")         // 15 min
    val TOTAL_CYCLES = intPreferencesKey("total_cycles")         // 4
}

data class PomodoroSettings(
    val pomodoroLenMs: Long = 25 * 60_000L,
    val shortBreakMs: Long = 5 * 60_000L,
    val longBreakMs: Long = 15 * 60_000L,
    val totalCycles: Int = 4
)

class SettingsDataStore(private val context: Context) {

    val pomodoroSettings: Flow<PomodoroSettings> =
        context.dataStore.data.map { p ->
            PomodoroSettings(
                pomodoroLenMs = p[Keys.POMO_LEN] ?: 25*60_000L,
                shortBreakMs = p[Keys.SHORT_BREAK] ?: 5*60_000L,
                longBreakMs = p[Keys.LONG_BREAK] ?: 15*60_000L,
                totalCycles = p[Keys.TOTAL_CYCLES] ?: 4
            )
        }

    suspend fun updatePomodoro(
        pomodoroLenMin: Int,
        shortBreakMin: Int,
        longBreakMin: Int,
        totalCycles: Int
    ) {
        context.dataStore.edit { p ->
            p[Keys.POMO_LEN] = pomodoroLenMin * 60_000L
            p[Keys.SHORT_BREAK] = shortBreakMin * 60_000L
            p[Keys.LONG_BREAK] = longBreakMin * 60_000L
            p[Keys.TOTAL_CYCLES] = totalCycles
        }
    }
}
