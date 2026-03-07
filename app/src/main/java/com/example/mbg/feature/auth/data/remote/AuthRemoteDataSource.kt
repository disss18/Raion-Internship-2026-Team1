package com.example.mbg.feature.auth.data.remote

interface AuthRemoteDataSource {

    suspend fun login(email: String, password: String)

    suspend fun register(email: String, password: String)

    suspend fun loginWithGoogle()

    suspend fun logout()

    suspend fun updateUserRole(role: String)

    suspend fun resetPassword(email: String): Result<Unit>

    suspend fun sendResetPasswordEmail(email: String)

    suspend fun updatePassword(password: String)
}