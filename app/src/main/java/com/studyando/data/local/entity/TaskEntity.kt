package com.studyando.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String = "",
    val dueAt: Long? = null,
    val priority: Int = 2,
    val tags: String = "",
    val isDone: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)