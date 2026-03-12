package com.example.mbg.core.di

import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.feature.auth.domain.AuthRepository

object AuthModule {

    private val remote by lazy {
        AuthRemoteDataSourceImpl()
    }

    val repository: AuthRepository by lazy {
        AuthRepositoryImpl(remote)
    }
}