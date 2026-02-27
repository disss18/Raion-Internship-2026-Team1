package com.example.mbg.supabase

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth

object SupabaseClientProvider {

    val client = createSupabaseClient(
        supabaseUrl = "https://ysnorbppodjswroxvjpj.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inlzbm9yYnBwb2Rqc3dyb3h2anBqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzIxNjgwNzYsImV4cCI6MjA4Nzc0NDA3Nn0.rRUr04wRQKh0h5LaTZa_EMwkGxd3-2-WEw6thBzwuno"
    ) {
        install(Auth)
    }
}