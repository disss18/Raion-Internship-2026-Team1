package com.example.mbg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.mbg.core.navigation.RootNavGraph
import com.example.mbg.supabase.SupabaseClientProvider
import com.example.mbg.ui.theme.MBGTheme
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserSession
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent)

        setContent {
            MBGTheme {
                RootNavGraph()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val dataString = intent?.dataString ?: return

        if (!dataString.contains("access_token")) return

        val uri = Uri.parse(dataString.replace("#", "?"))

        val accessToken = uri.getQueryParameter("access_token") ?: return
        val refreshToken = uri.getQueryParameter("refresh_token") ?: return
        val expiresIn =
            uri.getQueryParameter("expires_in")?.toLongOrNull() ?: 3600L

        lifecycleScope.launch {

            SupabaseClientProvider.client.auth.importSession(
                session = UserSession(
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    expiresIn = expiresIn,
                    tokenType = "bearer"
                )
            )
        }
    }
}