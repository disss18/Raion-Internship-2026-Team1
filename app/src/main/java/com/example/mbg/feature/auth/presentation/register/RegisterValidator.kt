package com.example.mbg.feature.auth.presentation.register

import android.util.Patterns

object RegisterValidator {

    fun validate(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): String? {

        if (name.isBlank()) {
            return "Nama tidak boleh kosong"
        }

        if (email.isBlank()) {
            return "Email tidak boleh kosong"
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Format email tidak valid"
        }

        if (password.length < 8) {
            return "Password minimal 8 karakter"
        }

        if (password != confirmPassword) {
            return "Password tidak sama"
        }

        return null
    }
}