package com.example.mbg.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.feature.auth.domain.AuthRepository
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GlobalAuthViewModel : ViewModel() {

    private val repository: AuthRepository =
        AuthRepositoryImpl(AuthRemoteDataSourceImpl())

    private val supabase = SupabaseClientProvider.client

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    private val _verificationStatus = MutableStateFlow<String?>(null)
    val verificationStatus: StateFlow<String?> = _verificationStatus

    init {
        observeSession()
    }

    private fun observeSession() {

        viewModelScope.launch {

            supabase.auth.sessionStatus.collect { status ->

                when (status) {

                    is SessionStatus.Authenticated -> {

                        val userId =
                            supabase.auth.currentUserOrNull()?.id

                        if (userId == null) {
                            _authState.value = AuthState.Unauthenticated
                            return@collect
                        }

                        try {

                            var role =
                                repository.getUserRole(userId).getOrNull()

                            // retry kecil supaya tidak race condition
                            if (role == null) {
                                delay(250)
                                role = repository.getUserRole(userId).getOrNull()
                            }

                            val verification =
                                repository.getDapurVerificationStatus().getOrNull()

                            _userRole.value = role
                            _verificationStatus.value = verification

                            if (role == null) {
                                _authState.value = AuthState.NeedRole
                            } else {
                                _authState.value = AuthState.Authenticated
                            }

                        } catch (e: Exception) {

                            _authState.value = AuthState.Unauthenticated
                        }
                    }

                    else -> {

                        _userRole.value = null
                        _verificationStatus.value = null
                        _authState.value = AuthState.Unauthenticated
                    }
                }
            }
        }
    }

    fun setVerificationPending() {

        _verificationStatus.value = "pending"

        _authState.value = AuthState.Authenticated
    }
}