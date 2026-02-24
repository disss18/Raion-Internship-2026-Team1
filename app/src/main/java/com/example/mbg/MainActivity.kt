package com.example.mbg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mbg.onboarding.presentation.OnboardingScreen
import com.example.mbg.ui.theme.MBGTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

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