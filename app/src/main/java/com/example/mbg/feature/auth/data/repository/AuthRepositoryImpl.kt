package com.example.mbg.feature.auth.data.repository

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSource
import com.example.mbg.feature.auth.domain.AuthRepository
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
) : AuthRepository {

    private val client = SupabaseClientProvider.client

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> =
        runCatching {
            remote.login(email, password)
        }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> =
        runCatching {
            remote.register(email, password)
        }

    override suspend fun loginWithGoogle(): Result<Unit> =
        runCatching {
            remote.loginWithGoogle()
        }

    override suspend fun logout() =
        runCatching {
            remote.logout()
        }

    override suspend fun getUserRole(): Result<String?> =
        runCatching {

            val userId = client.auth.currentUserOrNull()?.id
                ?: return@runCatching null

            val result = client
                .from("profiles")
                .select(
                    columns = Columns.list("role")
                ) {
                    filter {
                        eq("id", userId)
                    }
                }
                .decodeSingleOrNull<Map<String, String?>>()

            result?.get("role")
        }

    // 🔵 IMPLEMENTASI BARU
    override suspend fun getDapurVerificationStatus(): Result<String?> =
        runCatching {

            val userId = client.auth.currentUserOrNull()?.id
                ?: return@runCatching null

            val result = client
                .from("dapur_verifications")
                .select(
                    columns = Columns.list("status")
                ) {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeSingleOrNull<Map<String, String?>>()

            result?.get("status")
        }

    override suspend fun updateUserRole(role: String): Result<Unit> =
        runCatching {
            remote.updateUserRole(role)
        }

    override suspend fun resetPassword(email: String): Result<Unit> {
        return remote.resetPassword(email)
    }

    override suspend fun sendResetPasswordEmail(email: String) {
        remote.sendResetPasswordEmail(email)
    }

    override suspend fun updatePassword(password: String) {
        remote.updatePassword(password)
    }
}