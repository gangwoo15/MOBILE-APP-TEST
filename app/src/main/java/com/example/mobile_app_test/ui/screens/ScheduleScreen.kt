package com.example.mobile_app_test.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_app_test.data.Schedule
import com.example.mobile_app_test.ui.components.AddScheduleDialog
import com.example.mobile_app_test.ui.components.DeleteConfirmDialog
import com.example.mobile_app_test.ui.components.EditScheduleDialog
import com.example.mobile_app_test.ui.components.ScheduleItem
import com.example.mobile_app_test.ui.components.StatCard
import com.example.mobile_app_test.viewmodel.ScheduleViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = viewModel()
) {
    val currentDate = remember { Date() }
    val dateFormat = remember { SimpleDateFormat("yyyy년 M월 d일", Locale.KOREAN) }
    val dayFormat = remember { SimpleDateFormat("EEEE", Locale.KOREAN) }

    var showAddDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var scheduleToDelete by remember { mutableStateOf<Schedule?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var scheduleToEdit by remember { mutableStateOf<Schedule?>(null) }

    // ViewModel에서 데이터 가져오기
    val schedules by viewModel.allSchedules.collectAsState()
    val incompleteCount by viewModel.incompleteCount.collectAsState()
    val completedCount by viewModel.completedCount.collectAsState()
    val totalCount = schedules.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFEFF6FF),
                        Color(0xFFE0E7FF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 헤더 카드
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2563EB)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 왼쪽: 아이콘 + 제목
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Calendar",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                "나의 일정표",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                "${dateFormat.format(currentDate)} ${dayFormat.format(currentDate)}",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.85f)
                            )
                        }
                    }

                    // 오른쪽: 알림 아이콘
                    Icon(
                        Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // 통계 카드
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "전체 일정",
                    count = totalCount,
                    backgroundColor = Color(0xFFDCFCE7),
                    textColor = Color(0xFF16A34A),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "완료",
                    count = completedCount,
                    backgroundColor = Color(0xFFDCFCE7),
                    textColor = Color(0xFF16A34A),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "진행중",
                    count = incompleteCount,
                    backgroundColor = Color(0xFFFED7AA),
                    textColor = Color(0xFFEA580C),
                    modifier = Modifier.weight(1f)
                )
            }

            // 일정 목록 또는 빈 상태
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .heightIn(min = 200.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                if (schedules.isEmpty()) {
                    // 빈 상태
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            modifier = Modifier.size(96.dp),
                            shape = RoundedCornerShape(48.dp),
                            color = Color(0xFFF3F4F6)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription = "Empty",
                                    tint = Color(0xFF9CA3AF),
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "등록된 일정이 없습니다",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF374151)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "새로운 일정을 추가해보세요",
                            fontSize = 14.sp,
                            color = Color(0xFF6B7280)
                        )
                    }
                } else {
                    // 일정 목록
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        contentPadding = PaddingValues(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(schedules) { schedule ->
                            ScheduleItem(
                                schedule = schedule,
                                onToggleComplete = { viewModel.toggleComplete(it) },
                                onDelete = {
                                    scheduleToDelete = it
                                    showDeleteDialog = true
                                },
                                onEdit = {
                                    scheduleToEdit = it
                                    showEditDialog = true
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 추가 버튼
            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2563EB)
                )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "새 일정 추가",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "일정을 추가하고 효율적으로 관리해보세요",
                fontSize = 12.sp,
                color = Color(0xFF6B7280)
            )
        }
    }

    // 일정 추가 다이얼로그
    if (showAddDialog) {
        AddScheduleDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, description, dueDate ->
                viewModel.addSchedule(title, description, dueDate)
            }
        )
    }

    // 삭제 확인 다이얼로그
    if (showDeleteDialog && scheduleToDelete != null) {
        DeleteConfirmDialog(
            scheduleTitle = scheduleToDelete!!.title,
            onDismiss = {
                showDeleteDialog = false
                scheduleToDelete = null
            },
            onConfirm = {
                scheduleToDelete?.let { viewModel.deleteSchedule(it) }
                scheduleToDelete = null
            }
        )
    }

    // 일정 수정 다이얼로그
    if (showEditDialog && scheduleToEdit != null) {
        EditScheduleDialog(
            schedule = scheduleToEdit!!,
            onDismiss = {
                showEditDialog = false
                scheduleToEdit = null
            },
            onConfirm = { title, description, dueDate ->
                scheduleToEdit?.let { schedule ->
                    val updatedSchedule = schedule.copy(
                        title = title,
                        description = description,
                        dueDate = dueDate
                    )
                    viewModel.updateSchedule(updatedSchedule)
                }
                scheduleToEdit = null
            }
        )
    }
}