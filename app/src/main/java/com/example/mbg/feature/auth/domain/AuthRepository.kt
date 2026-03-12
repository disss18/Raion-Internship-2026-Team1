package com.example.mbg.feature.auth.domain

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun register(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun loginWithGoogle(): Result<Unit>

    suspend fun logout(): Result<Unit>

    suspend fun getUserRole(userId: String): Result<String?>

    suspend fun getDapurVerificationStatus(): Result<String?>

    suspend fun updateUserRole(role: String): Result<Unit>

    suspend fun resetPassword(email: String): Result<Unit>

    suspend fun sendResetPasswordEmail(email: String)

    suspend fun updatePassword(password: String)

}