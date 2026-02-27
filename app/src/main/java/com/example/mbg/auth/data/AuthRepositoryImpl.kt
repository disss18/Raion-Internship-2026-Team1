package com.example.mbg.auth.data

import com.example.mbg.auth.domain.AuthRepository
import com.example.mbg.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl : AuthRepository {

    private val client = SupabaseClientProvider.client

    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> = withContext(Dispatchers.IO) {

        return@withContext try {

            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> = withContext(Dispatchers.IO) {

        return@withContext try {

            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}