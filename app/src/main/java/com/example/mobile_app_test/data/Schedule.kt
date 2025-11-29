package com.example.mobile_app_test.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val dueDate: Long, // 마감일 (timestamp)
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    // 남은 날짜 계산
    fun getDaysRemaining(): Int {
        val today = Date().time
        val due = Date(dueDate).time
        val diff = due - today
        return (diff / (1000 * 60 * 60 * 24)).toInt()
    }

    // 마감일 지났는지 확인
    fun isOverdue(): Boolean {
        return !isCompleted && Date().time > dueDate
    }
}