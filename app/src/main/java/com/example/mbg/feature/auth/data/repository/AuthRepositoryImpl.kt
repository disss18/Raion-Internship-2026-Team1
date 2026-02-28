package com.example.mbg.feature.auth.data.repository

import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSource
import com.example.mbg.feature.auth.domain.AuthRepository

class AuthRepositoryImpl(
    private val remote: AuthRemoteDataSource
) : AuthRepository {

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

    override suspend fun logout() {
        remote.logout()
    }
}