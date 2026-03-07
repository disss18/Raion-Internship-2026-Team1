package com.example.mbg.core.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.mbg.R

@Composable
fun DashboardBottomBar() {

    NavigationBar(
        containerColor = Color.White
    ) {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.beranda_botom),
                    contentDescription = "Menu"
                )
            },
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.menu_bottom),
                    contentDescription = "Menu"
                )
            },
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.distribusi_bottom),
                    contentDescription = "Menu"
                )
            },
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.profil_bottom),
                    contentDescription = "Profil"
                )
            },
        )
    }
}