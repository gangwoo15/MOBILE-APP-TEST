package com.example.mobile_app_test.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_app_test.data.Schedule
import com.example.mobile_app_test.data.ScheduleDatabase
import com.example.mobile_app_test.data.ScheduleRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ScheduleRepository

    val allSchedules: StateFlow<List<Schedule>>
    val incompleteCount: StateFlow<Int>
    val completedCount: StateFlow<Int>

    init {
        val scheduleDao = ScheduleDatabase.getDatabase(application).scheduleDao()
        repository = ScheduleRepository(scheduleDao)

        allSchedules = repository.allSchedules.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        incompleteCount = repository.incompleteCount.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

        completedCount = repository.completedCount.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }

    fun addSchedule(title: String, description: String, dueDate: Long) {
        viewModelScope.launch {
            val schedule = Schedule(
                title = title,
                description = description,
                dueDate = dueDate
            )
            repository.insert(schedule)
        }
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch {
            repository.update(schedule)
        }
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch {
            repository.delete(schedule)
        }
    }

    fun toggleComplete(schedule: Schedule) {
        viewModelScope.launch {
            repository.toggleComplete(schedule)
        }
    }
}