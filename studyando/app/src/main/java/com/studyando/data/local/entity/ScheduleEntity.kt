package com.studyando.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subject: String,
    val location: String?,
    val dayOfWeek: Int,
    val startMinutes: Int,
    val endMinutes: Int,
    val color: Int
)