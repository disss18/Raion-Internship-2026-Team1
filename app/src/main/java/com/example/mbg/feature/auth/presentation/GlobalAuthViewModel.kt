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
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

sealed class AuthState {
    object Loading : AuthState()
    object Unauthenticated : AuthState()
    object Authenticated : AuthState()
    object NeedRole : AuthState()
}

class GlobalAuthViewModel : ViewModel() {
    private val repository: AuthRepository = AuthRepositoryImpl(AuthRemoteDataSourceImpl())
    private val supabase = SupabaseClientProvider.client

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    private val _verificationStatus = MutableStateFlow<String?>(null)
    val verificationStatus: StateFlow<String?> = _verificationStatus

    private var roleLoaded = false

    init { observeSession() }

    private fun observeSession() {
        viewModelScope.launch {
            supabase.auth.sessionStatus.collect { status ->
                if (status is SessionStatus.Authenticated) {
                    refreshAllData()
                } else {
                    roleLoaded = false
                    _userRole.value = null
                    _verificationStatus.value = null
                    _authState.value = AuthState.Unauthenticated
                }
            }
        }
    }

    fun refreshAllData() {
        viewModelScope.launch {
            try {
                val userId = supabase.auth.currentSessionOrNull()?.user?.id
                if (userId == null) {
                    _authState.value = AuthState.Unauthenticated
                    return@launch
                }

                // Trik Staging: delay kecil anti race-condition
                var role = repository.getUserRole(userId).getOrNull()
                if (role == null) {
                    delay(250)
                    role = repository.getUserRole(userId).getOrNull()
                }

                _userRole.value = role

                if (role == "DAPUR_MBG") {
                    updateVerificationStatus(userId)
                }

                _authState.value = if (role != null) AuthState.Authenticated else AuthState.NeedRole
            } catch (e: Exception) {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    // Fix Farrell + param userId
    fun updateVerificationStatus(userId: String? = null) {
        viewModelScope.launch {
            try {
                val currentUserId = userId ?: supabase.auth.currentSessionOrNull()?.user?.id
                if (currentUserId != null) {
                    val verifList = supabase.from("dapur_verifications")
                        .select {
                            filter { eq("user_id", currentUserId) }
                            order("created_at", Order.DESCENDING)
                        }.decodeList<JsonObject>()

                    val statusStr = verifList.firstOrNull()?.get("status")?.jsonPrimitive?.content ?: "pending"

                    _verificationStatus.value = statusStr
                    SessionManager.setVerificationStatus(statusStr)
                }
            } catch (e: Exception) {
                e.printStackTrace()
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