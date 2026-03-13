package com.example.mbg.core.navigation

sealed class Screen(val route: String) {

    // Root
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Welcome : Screen("welcome")

    // Auth
    object Auth : Screen("auth_graph")
    object Login : Screen("login")
    object Register : Screen("register")
    object Role : Screen("role")
    object ForgotPassword : Screen("forgot_password")
    object ResetPassword : Screen("reset_password")

    object VerificationMBG : Screen("verification_mbg")
    object VerificationStatus : Screen("verification_status")

    // Main Graph
    object Main : Screen("main_graph")

    object Home : Screen("home")

    object DashboardMBG : Screen("dashboard_mbg")

    object DashboardSekolah : Screen("dashboard_sekolah")

    object DashboardOrangTua : Screen("dashboard_orang_tua")

    object Feedback : Screen("feedback")

    // NEW SCREEN
    object SchoolStudent : Screen("school_student")

    object Distribution : Screen("distribution")
}