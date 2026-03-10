package com.example.mbg.core.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SessionManager {

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole

    private val _verificationStatus = MutableStateFlow<String?>(null)
    val verificationStatus: StateFlow<String?> = _verificationStatus

    fun setUserRole(role: String?) {
        _userRole.value = role
    }

    fun setVerificationStatus(status: String?) {
        _verificationStatus.value = status
    }

    fun clearSession() {
        _userRole.value = null
        _verificationStatus.value = null
    }
}