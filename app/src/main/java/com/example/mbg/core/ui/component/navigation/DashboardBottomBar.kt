package com.example.mbg.core.ui.component

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mbg.core.navigation.BottomNavItem
import androidx.compose.runtime.*

@Composable
fun DashboardBottomBar(
    items: List<BottomNavItem>
) {

    var selectedIndex by remember { mutableStateOf(0) }

    NavigationBar {

        items.forEachIndexed { index, item ->

            NavigationBarItem(

                selected = selectedIndex == index,

                onClick = {
                    selectedIndex = index
                },

                icon = {

                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label
                    )

                },
            )
        }
    }
}