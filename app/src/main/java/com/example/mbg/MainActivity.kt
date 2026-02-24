package com.example.mbg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mbg.onboarding.presentation.OnboardingScreen
import com.example.mbg.ui.theme.MBGTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MBGTheme {
                OnboardingScreen(
                    onFinish = {}
                )
            }
        }
    }
}