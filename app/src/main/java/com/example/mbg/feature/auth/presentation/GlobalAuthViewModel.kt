package com.example.mbg.feature.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Loading : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
}

class GlobalAuthViewModel : ViewModel() {

    private val supabase = SupabaseClientProvider.client

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    init {
        observeSession()
    }

    private fun observeSession() {
        viewModelScope.launch {

            supabase.auth.sessionStatus.collect { status ->

                Log.d("AUTH_DEBUG", "SessionStatus = $status")

                _authState.value =
                    when (status) {
                        is SessionStatus.Authenticated -> {
                            Log.d("AUTH_DEBUG", "User AUTHENTICATED")
                            AuthState.Authenticated
                        }

                        is SessionStatus.NotAuthenticated -> {
                            Log.d("AUTH_DEBUG", "User NOT AUTHENTICATED")
                            AuthState.Unauthenticated
                        }

                        else -> {
                            Log.d("AUTH_DEBUG", "Session Loading")
                            AuthState.Loading
                        }
                    }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            supabase.auth.signOut()
        }
    }
}