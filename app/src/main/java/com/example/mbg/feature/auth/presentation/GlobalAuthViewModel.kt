package com.example.mbg.feature.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GlobalAuthViewModel : ViewModel() {

    private val supabase = SupabaseClientProvider.client

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    init {
        observeSession()
    }

    private fun observeSession() {
        viewModelScope.launch {

            supabase.auth.sessionStatus.collect { status ->

                _isLoggedIn.value =
                    status is SessionStatus.Authenticated
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            supabase.auth.signOut()
        }
    }
}