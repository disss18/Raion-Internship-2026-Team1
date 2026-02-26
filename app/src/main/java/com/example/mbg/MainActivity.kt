package com.example.mbg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.mbg.splashscreen.AnimatedSplashScreen
import com.example.mbg.splashscreen.WelcomeScreen
import com.example.mbg.ui.theme.MBGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            MBGTheme {
                var currentScreen by remember { mutableStateOf("splash") }

                androidx.compose.animation.Crossfade(
                    targetState = currentScreen,
                    animationSpec = androidx.compose.animation.core.tween(durationMillis = 800),
                    label = "transisi_layar"
                )
                { screen ->
                    when (screen) {

                        "splash" -> {
                            AnimatedSplashScreen(
                                onNavigateToWelcome = {
                                    currentScreen = "welcome"
                                }
                            )
                        }

                        "welcome" -> {
                            WelcomeScreen(
                                onNavigateToLogin = {
                                    currentScreen = "login"
                                },
                                onNavigateToRegister = {
                                    currentScreen = "register"
                                }
                            )
                        }

                        // test
                        "login" -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Halaman masuk", fontSize = 24.sp)
                            }
                        }

                        "register" -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Halaman daftar", fontSize = 24.sp)
                            }
                        }

                    }
                }
            }
        }
    }
}