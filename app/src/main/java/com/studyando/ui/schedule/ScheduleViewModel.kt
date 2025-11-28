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
class ScheduleViewModel @Inject constructor(
    private val repository: StudyRepository
) : ViewModel() {

    fun getClassesByDay(day: Int): Flow<List<ScheduleEntity>> {
        return repository.getClassesByDay(day)
    }

    fun addClassSession(subject: String, location: String?, day: Int, start: Int, end: Int) {
        viewModelScope.launch {
            val session = ScheduleEntity(
                subject = subject,
                location = location,
                dayOfWeek = day,
                startMinutes = start,
                endMinutes = end
            )
            repository.insertClassSession(session)
        }
    }
}