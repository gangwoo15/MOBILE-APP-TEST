package com.example.mobile_app_test.ui.theme  // 이렇게 수정!

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2563EB),
    secondary = Color(0xFF4F46E5),
    background = Color(0xFFFAFAFA),
    surface = Color.White
)

@Composable
fun ScheduleAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}