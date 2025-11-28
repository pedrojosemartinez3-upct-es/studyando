package com.studyando.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.studyando.data.local.dao.TaskDao
import com.studyando.data.local.dao.ScheduleDao
import com.studyando.data.local.entity.TaskEntity
import com.studyando.data.local.entity.ScheduleEntity

@Database(
    entities = [TaskEntity::class, ScheduleEntity::class],
    version = 2,
    exportSchema = false
)
abstract class StudyandoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun scheduleDao(): ScheduleDao
}
