package com.example.mbg.feature.auth.presentation.login

import android.util.Patterns

object LoginValidator {

    fun validate(email: String, password: String): String? {

        if (email.isBlank()) {
            return "Email tidak boleh kosong"
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Format email tidak valid"
        }

        if (password.isBlank()) {
            return "Password tidak boleh kosong"
        }

        if (password.length < 8) {
            return "Password minimal 8 karakter"
        }

        return null
    }
}