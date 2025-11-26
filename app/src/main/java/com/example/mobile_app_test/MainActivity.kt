package com.example.mobile_app_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobile_app_test.ui.theme.ScheduleAppTheme
import com.example.mobile_app_test.ui.screens.ScheduleScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScheduleAppTheme {
                ScheduleScreen()
            }
        }
    }
}