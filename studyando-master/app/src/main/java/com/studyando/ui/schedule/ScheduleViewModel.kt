package com.studyando.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studyando.data.local.entity.ScheduleEntity
import com.studyando.data.repo.StudyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val repo: StudyRepository) : ViewModel() {

    fun getClassesByDay(day: Int): Flow<List<ScheduleEntity>> = repo.getClassesByDay(day)

    fun addClass(sub: String, loc: String?, day: Int, start: Int, end: Int, color: Int) {
        viewModelScope.launch {
            repo.insertClass(ScheduleEntity(0, sub, loc, day, start, end, color))
        }
    }

    fun updateClass(item: ScheduleEntity) = viewModelScope.launch { repo.updateClass(item) }

    fun deleteClass(item: ScheduleEntity) = viewModelScope.launch { repo.deleteClass(item) }
}