package com.example.mobile_app_test.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_app_test.data.Schedule
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ScheduleItem(
    schedule: Schedule,
    onToggleComplete: (Schedule) -> Unit,
    onDelete: (Schedule) -> Unit,
    onEdit: (Schedule) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("yyyy년 M월 d일", Locale.KOREAN)
    val daysRemaining = schedule.getDaysRemaining()
    val isOverdue = schedule.isOverdue()

    // 카드 배경색 (완료/미완료/기한 초과)
    val cardColor = when {
        schedule.isCompleted -> Color(0xFFF3F4F6)
        isOverdue -> Color(0xFFFEE2E2)
        daysRemaining <= 3 -> Color(0xFFFEF3C7)
        else -> Color.White
    }

    // 상태 색상
    val statusColor = when {
        schedule.isCompleted -> Color(0xFF10B981)
        isOverdue -> Color(0xFFEF4444)
        daysRemaining <= 3 -> Color(0xFFF59E0B)
        else -> Color(0xFF3B82F6)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // 완료 체크 아이콘
                IconButton(
                    onClick = { onToggleComplete(schedule) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (schedule.isCompleted) Icons.Default.CheckCircle else Icons.Default.Circle,
                        contentDescription = "Complete",
                        tint = statusColor,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // 제목 & 설명
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = schedule.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (schedule.isCompleted) Color.Gray else Color(0xFF1F2937),
                        textDecoration = if (schedule.isCompleted) TextDecoration.LineThrough else null
                    )

                    if (schedule.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = schedule.description,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            maxLines = 2
                        )
                    }
                }

                // 수정/삭제 버튼
                Row {
                    IconButton(
                        onClick = { onEdit(schedule) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { onDelete(schedule) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 마감일 정보
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 마감일 표시
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = statusColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = dateFormat.format(Date(schedule.dueDate)),
                            fontSize = 12.sp,
                            color = statusColor,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // 남은 날짜 또는 상태 표시
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = statusColor
                ) {
                    val statusText = when {
                        schedule.isCompleted -> "완료"
                        isOverdue -> "기한 초과"
                        daysRemaining == 0 -> "오늘 마감"
                        daysRemaining == 1 -> "내일 마감"
                        else -> "D-${daysRemaining}"
                    }

                    Text(
                        text = statusText,
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}