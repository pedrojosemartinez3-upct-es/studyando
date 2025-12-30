package com.studyando.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyando.data.local.entity.TaskEntity
import com.studyando.data.repo.StudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val repo: StudyRepository) : ViewModel() {

    val openTasks = repo.openTasks
    val completedTasks = repo.completedTasks

    fun addTask(title: String, desc: String, priority: Int, due: Long?) {
        viewModelScope.launch {
            repo.addTask(TaskEntity(title = title, description = desc, priority = priority, dueAt = due))
        }
    }

    fun onTaskDoneToggled(task: TaskEntity) {
        viewModelScope.launch {
            repo.updateTask(task.copy(isDone = !task.isDone))
        }
    }

    fun updateTask(task: TaskEntity) = viewModelScope.launch { repo.updateTask(task) }

    fun deleteTask(task: TaskEntity) = viewModelScope.launch { repo.deleteTask(task) }
}