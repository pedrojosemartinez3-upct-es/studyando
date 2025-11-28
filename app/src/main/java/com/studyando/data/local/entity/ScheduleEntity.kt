package com.studyando.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subject: String,
    val location: String?,
    val dayOfWeek: Int,     // 1..7 (Lunes=1)
    val startMinutes: Int,  // minutos desde 00:00
    val endMinutes: Int
)