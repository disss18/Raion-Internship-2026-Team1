package com.example.mbg.core.navigation

import com.example.mbg.R

object BottomNavConfig {

    /**
     * ROLE MBG
     */
    val mbg = listOf(
        BottomNavItem(
            label = "Beranda",
            icon = R.drawable.beranda_botom,
            route = Screen.DashboardMBG.route
        ),
        BottomNavItem(
            label = "Menu",
            icon = R.drawable.menu_bottom,
            route = Screen.InputGizi.route
        ),

        BottomNavItem(
            label = "Distribusi",
            icon = R.drawable.distribusi_bottom,
            route = Screen.Distribution.route
        ),
        BottomNavItem(
            label = "Profil",
            icon = R.drawable.profil_bottom,
            route = Screen.Profile.route
        )
    )

    /**
     * ROLE SEKOLAH
     */
    val school = listOf(
        BottomNavItem(
            label = "Beranda",
            icon = R.drawable.beranda_botom,
            route = Screen.DashboardSekolah.route
        ),
        BottomNavItem(
            label = "Siswa",
            icon = R.drawable.siswa_bottom,
            route = Screen.SchoolStudent.route
        ),
        BottomNavItem(
            label = "Distribusi",
            icon = R.drawable.distribusi_bottom,
            route = Screen.Distribution.route
        ),
        BottomNavItem(
            label = "Profil",
            icon = R.drawable.profil_bottom,
            route = Screen.Profile.route
        )
    )

    /**
     * ROLE ORANG TUA
     */
    val parent = listOf(
        BottomNavItem(
            label = "Beranda",
            icon = R.drawable.beranda_botom,
            route = Screen.DashboardOrangTua.route
        ),
        BottomNavItem(
            label = "Menu",
            icon = R.drawable.menu_bottom,
            route = Screen.Home.route
        ),
        BottomNavItem(
            label = "Aktivitas",
            icon = R.drawable.aktivitas_bottom,
            route = Screen.Reward.route
        ),
        BottomNavItem(
            label = "Profil",
            icon = R.drawable.profil_bottom,
            route = Screen.Profile.route
        )
    )
}