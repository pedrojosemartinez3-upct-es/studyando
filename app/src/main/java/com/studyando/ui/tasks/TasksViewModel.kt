package com.studyando.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyando.data.local.entity.TaskEntity
import com.studyando.data.repo.StudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val repository: StudyRepository
) : ViewModel() {

    val openTasks: Flow<List<TaskEntity>> = repository.openTasks
    val completedTasks: Flow<List<TaskEntity>> = repository.completedTasks

    // --- ESTA ES LA FUNCIÓN QUE TE FALTABA ---
    fun addTask(title: String, description: String, priority: Int, dueAt: Long?) {
        viewModelScope.launch {
            repository.addTask(
                TaskEntity(
                    title = title,
                    description = description,
                    priority = priority,
                    dueAt = dueAt
                )
            )
        }
    }

    fun onTaskDoneToggled(task: TaskEntity) {
        viewModelScope.launch {
            repository.toggleDone(task)
        }
    }

    // (Aquí puedes añadir updateTask, deleteTask, etc.)
}