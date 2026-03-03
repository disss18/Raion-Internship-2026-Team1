package com.example.mbg.feature.auth.data.remote

import com.example.mbg.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthRemoteDataSource {

    private val client = SupabaseClientProvider.client

    suspend fun login(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun register(email: String, password: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun loginWithGoogle() {
        client.auth.signInWith(Google)
    }

    suspend fun logout() {
        client.auth.signOut()
    }
}