package com.example.mbg.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.core.session.SessionManager
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.feature.auth.domain.AuthRepository
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object NeedRole : AuthState()
}

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

    // Guard supaya role tidak hilang ketika session refresh
    private var roleLoaded = false

    init {
        observeSession()
    }

    private fun observeSession() {

        viewModelScope.launch {

            supabase.auth.sessionStatus.collect { status ->

                when (status) {

                    is SessionStatus.Authenticated -> {

                        val session = supabase.auth.currentSessionOrNull()

                        if (session == null) {
                            _authState.value = AuthState.Unauthenticated
                            return@collect
                        }

                        try {

                            val roleResult = repository.getUserRole()
                            val role = roleResult.getOrNull()

                            if (role != null) {

                                roleLoaded = true

                                SessionManager.setUserRole(role)

                                _userRole.value = role

                                if (role == "DAPUR_MBG") {

                                    val verificationResult =
                                        repository.getDapurVerificationStatus()

                                    val verification =
                                        verificationResult.getOrNull()

                                    _verificationStatus.value = verification

                                    SessionManager.setVerificationStatus(
                                        verification
                                    )
                                }

                                _authState.value = AuthState.Authenticated

                            } else {

                                if (!roleLoaded) {
                                    _authState.value = AuthState.NeedRole
                                }
                            }

                        } catch (e: Exception) {

                            if (roleLoaded) {
                                _authState.value = AuthState.Authenticated
                            } else {
                                _authState.value = AuthState.Loading
                            }
                        }
                    }

                    else -> {

                        roleLoaded = false

                        SessionManager.clearSession()

                        _authState.value = AuthState.Unauthenticated
                    }
                }
            }
        }
    }

    fun logout() {

        viewModelScope.launch {

            repository.logout()

            SessionManager.clearSession()

            roleLoaded = false

            _authState.value = AuthState.Unauthenticated
        }
    }

    fun setVerificationPending() {

        _verificationStatus.value = "pending"

        SessionManager.setVerificationStatus("pending")

        _authState.value = AuthState.Authenticated
    }
}