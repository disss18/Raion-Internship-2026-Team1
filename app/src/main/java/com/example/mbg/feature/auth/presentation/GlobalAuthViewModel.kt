package com.example.mbg.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSource
import com.example.mbg.feature.auth.data.remote.AuthRemoteDataSourceImpl
import com.example.mbg.feature.auth.data.repository.AuthRepositoryImpl
import com.example.mbg.feature.auth.domain.AuthRepository
import com.example.mbg.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object NeedRole : AuthState() // TAMBAHAN
}

class GlobalAuthViewModel : ViewModel() {

    private val repository: AuthRepository =
        AuthRepositoryImpl(AuthRemoteDataSourceImpl())

    private val supabase = SupabaseClientProvider.client

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    init {
        observeSession()
    }

    private fun observeSession() {
        viewModelScope.launch {

            supabase.auth.sessionStatus.collect { status ->

                if (status is SessionStatus.Authenticated) {

                    val roleResult = repository.getUserRole()
                    val role = roleResult.getOrNull()

                    _authState.value =
                        if (role == null) {
                            AuthState.NeedRole
                        } else {
                            AuthState.Authenticated
                        }

                } else {
                    _authState.value = AuthState.Unauthenticated
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}