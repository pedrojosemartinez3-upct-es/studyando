package com.studyando.data.local.dao

import androidx.room.*
import com.studyando.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isDone = 0 ORDER BY priority, COALESCE(dueAt, 9223372036854775807)")
    fun observeOpenTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isDone = 1 ORDER BY dueAt DESC")
    fun observeCompletedTasks(): Flow<List<TaskEntity>>

    @Insert suspend fun insert(task: TaskEntity): Long
    @Update suspend fun update(task: TaskEntity)
    @Delete suspend fun delete(task: TaskEntity)

    @Query("UPDATE tasks SET isDone = 1 WHERE id = :id")
    suspend fun markDone(id: Long)
}