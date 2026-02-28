package com.example.mbg.feature.auth.presentation.login

import android.util.Patterns

object LoginValidator {

    fun validate(email: String, password: String): String? {

        // ================= BOTH EMPTY =================
        if (email.isBlank() && password.isBlank()) {
            return "Mohon isi email dan password anda"
        }

        // ================= EMAIL EMPTY =================
        if (email.isBlank()) {
            return "Email tidak boleh kosong"
        }

        // ================= EMAIL FORMAT =================
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Format email tidak valid"
        }

        // ================= PASSWORD EMPTY =================
        if (password.isBlank()) {
            return "Password tidak boleh kosong"
        }

        // ================= PASSWORD LENGTH =================
        if (password.length < 8) {
            return "Password minimal 8 karakter"
        }

        return null
    }
}