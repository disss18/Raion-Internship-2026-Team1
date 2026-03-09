package com.example.mbg.core.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SessionManager {

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    fun setUserRole(role: String?) {
        _userRole.value = role
    }

    fun clearSession() {
        _userRole.value = null
    }
}