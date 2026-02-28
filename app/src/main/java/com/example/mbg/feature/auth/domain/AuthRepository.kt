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

    suspend fun logout()
}