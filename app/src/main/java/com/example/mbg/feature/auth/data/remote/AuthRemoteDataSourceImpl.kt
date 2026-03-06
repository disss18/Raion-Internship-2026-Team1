package com.example.mbg.feature.auth.data.remote

import android.util.Log
import com.example.mbg.supabase.SupabaseClientProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest

class AuthRemoteDataSourceImpl(
    private val client: SupabaseClient = SupabaseClientProvider.client
) : AuthRemoteDataSource {

    override suspend fun login(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun register(email: String, password: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun loginWithGoogle() {
        client.auth.signInWith(Google)
    }

    override suspend fun logout() {

        Log.d("AUTH_DEBUG", "logout start")
        client.auth.signOut()
        client.auth.clearSession()
        Log.d("AUTH_DEBUG", "logout end")
    }

    override suspend fun updateUserRole(role: String) {

        val session = client.auth.currentSessionOrNull()
            ?: throw Exception("No session")

        val accessToken = session.accessToken

        // decode JWT manually
        val userId = accessToken
            .split(".")[1]
            .let { payload ->
                String(android.util.Base64.decode(payload, android.util.Base64.URL_SAFE))
            }
            .let { json ->
                org.json.JSONObject(json).getString("sub")
            }

        Log.d("ROLE_DEBUG", "USER ID FROM JWT: $userId")

        client
            .from("profiles")
            .update(
                mapOf(
                    "role" to role
                )
            ) {
                filter {
                    eq("id", userId)
                }
            }

        Log.d("ROLE_DEBUG", "ROLE UPDATED SUCCESS")
    }

    // ================= RESET PASSWORD =================

    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {

            client.auth.resetPasswordForEmail(
                email = email,
                redirectUrl = "mbg://reset-password"
            )

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendResetPasswordEmail(
        email: String
    ) {

        client.auth.resetPasswordForEmail(
            email = email,
            redirectUrl = "mbg://reset-password"
        )

    }

    // ================= UPDATE PASSWORD =================

    override suspend fun updatePassword(
        password: String
    ) {

        client.auth.updateUser {
            this.password = password
        }

    }
}