package com.example.mbg.feature.home.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mbg.feature.auth.presentation.GlobalAuthViewModel
import com.example.mbg.core.navigation.Screen
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.ui.theme.BlueNormal

@Composable
fun HomeScreen(
    navController: NavHostController
) {

    val authViewModel: GlobalAuthViewModel = viewModel()

    Scaffold { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // Tombol di bawah tengah
            PrimaryButton(
                text = "Logout",
                containerColor = BlueNormal,
                modifier = Modifier
                    .align(androidx.compose.ui.Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                onClick = {

                    authViewModel.logout()

                    navController.navigate(Screen.Auth.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}