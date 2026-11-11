package com.studyando.data.repo

import com.studyando.data.local.StudyandoDatabase
import com.studyando.data.local.entity.ScheduleEntity
import com.studyando.data.local.entity.TaskEntity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StudyRepository @Inject constructor(private val db: StudyandoDatabase) {
    val openTasks = db.taskDao().observeOpenTasks()
    val completedTasks = db.taskDao().observeCompletedTasks()

    suspend fun updateTask(task: TaskEntity) = db.taskDao().update(task)
    suspend fun deleteTask(task: TaskEntity) = db.taskDao().delete(task)
    suspend fun addTask(task: TaskEntity) = db.taskDao().insert(task)
    suspend fun toggleDone(task: TaskEntity) = db.taskDao().update(task)
    fun getClassesByDay(day: Int) = db.scheduleDao().getClassesByDay(day)
    fun getAllTasks() = db.taskDao().getAllTasks()

    suspend fun insertClass(session: ScheduleEntity) = db.scheduleDao().insert(session)
    suspend fun updateClass(session: ScheduleEntity) = db.scheduleDao().update(session)
    suspend fun deleteClass(session: ScheduleEntity) = db.scheduleDao().delete(session)
}
