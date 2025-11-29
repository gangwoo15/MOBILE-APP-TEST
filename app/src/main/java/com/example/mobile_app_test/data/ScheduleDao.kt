package com.example.mobile_app_test.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    // 완료된 일정은 하단으로 정렬
    @Query("SELECT * FROM schedules ORDER BY isCompleted ASC, dueDate ASC")
    fun getAllSchedules(): Flow<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: Schedule)

    @Update
    suspend fun updateSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT COUNT(*) FROM schedules WHERE isCompleted = 0")
    fun getIncompleteCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM schedules WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>
}