package com.example.mbg.core.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth

object SupabaseClientProvider {

    val client = createSupabaseClient(
        supabaseUrl = "https://ysnorbppodjswroxvjpj.supabase.co",
        supabaseKey = "sb_publishable_PU-yFukEl8gAUhQuJxPHbA_-eTiWwtX"
    ) {
        install(Auth)
    }
}