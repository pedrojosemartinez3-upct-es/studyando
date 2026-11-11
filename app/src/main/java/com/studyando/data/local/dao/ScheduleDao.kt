package com.studyando.data.local.dao

import androidx.room.*
import com.studyando.data.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule WHERE dayOfWeek = :day ORDER BY startMinutes ASC")
    fun getClassesByDay(day: Int): Flow<List<ScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: ScheduleEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(session: ScheduleEntity)

    @Delete
    suspend fun delete(session: ScheduleEntity)

}