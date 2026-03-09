package com.example.mbg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.example.mbg.core.navigation.RootNavGraph
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.ui.theme.MBGTheme
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private var deepLinkRoute by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.hide()
        enableEdgeToEdge()

        handleIntent(intent)

        setContent {

            MBGTheme {

                RootNavGraph(
                    deepLinkRoute = deepLinkRoute
                )

            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {

        val dataString = intent?.dataString ?: return

        val uri = Uri.parse(dataString.replace("#", "?"))

        val accessToken = uri.getQueryParameter("access_token") ?: return
        val refreshToken = uri.getQueryParameter("refresh_token") ?: return
        val expiresIn =
            uri.getQueryParameter("expires_in")?.toLongOrNull() ?: 3600L

        lifecycleScope.launch {

            // Import session Supabase
            SupabaseClientProvider.client.auth.importSession(
                session = UserSession(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    expiresIn = expiresIn,
                    tokenType = "bearer"
                )
            )

            // ================= HANDLE ROUTE =================

            when {

                dataString.contains("reset-password") -> {
                    deepLinkRoute = "reset_password"
                }

                dataString.contains("login-callback") -> {
                    deepLinkRoute = null
                }
            }
        }
    }
}