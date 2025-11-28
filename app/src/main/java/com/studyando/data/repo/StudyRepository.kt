package com.studyando.data.repo

import com.studyando.data.local.StudyandoDatabase
import com.studyando.data.local.entity.ScheduleEntity // ¡Importar!
import com.studyando.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyRepository @Inject constructor(
    private val db: StudyandoDatabase // Room se encarga de los DAOs
) {
    // --- Tareas ---
    val openTasks: Flow<List<TaskEntity>> = db.taskDao().observeOpenTasks()

    // (Añade esto para el CompletedFragment)
    val completedTasks: Flow<List<TaskEntity>> = db.taskDao().observeCompletedTasks()

    suspend fun addTask(task: TaskEntity) = db.taskDao().insert(task)
    suspend fun updateTask(task: TaskEntity) = db.taskDao().update(task)
    suspend fun toggleDone(task: TaskEntity) =
        db.taskDao().update(task.copy(isDone = !task.isDone))

    // --- Horario (Schedule) ---
    fun getClassesByDay(day: Int): Flow<List<ScheduleEntity>> =
        db.scheduleDao().getClassesByDay(day)

    suspend fun insertClassSession(session: ScheduleEntity) =
        db.scheduleDao().insert(session)
}
