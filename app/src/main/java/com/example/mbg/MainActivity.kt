package com.example.mbg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mbg.feature.home.presentation.mbg.DashboardMBGScreen
import com.example.mbg.ui.theme.MBGTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MBGTheme {

                DashboardMBGScreen()

            }

        }
    }
}