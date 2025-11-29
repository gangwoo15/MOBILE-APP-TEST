package com.example.mobile_app_test.data

import kotlinx.coroutines.flow.Flow

class ScheduleRepository(private val scheduleDao: ScheduleDao) {

    val allSchedules: Flow<List<Schedule>> = scheduleDao.getAllSchedules()
    val incompleteCount: Flow<Int> = scheduleDao.getIncompleteCount()
    val completedCount: Flow<Int> = scheduleDao.getCompletedCount()

    suspend fun insert(schedule: Schedule) {
        scheduleDao.insertSchedule(schedule)
    }

    suspend fun update(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule)
    }

    suspend fun delete(schedule: Schedule) {
        scheduleDao.deleteSchedule(schedule)
    }

    suspend fun toggleComplete(schedule: Schedule) {
        val updatedSchedule = schedule.copy(isCompleted = !schedule.isCompleted)
        scheduleDao.updateSchedule(updatedSchedule)
    }
}
