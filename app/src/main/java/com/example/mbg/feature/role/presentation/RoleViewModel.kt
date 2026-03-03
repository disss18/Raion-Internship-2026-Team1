package com.example.mbg.feature.role.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.feature.role.domain.model.UserRole
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch

class RoleViewModel(
    private val supabase: SupabaseClient
) : ViewModel() {

    fun saveRole(role: UserRole, onSuccess: () -> Unit) {
        viewModelScope.launch {

            val userId = supabase.auth.currentUserOrNull()?.id ?: return@launch

            supabase.from("profiles").update(
                {
                    set("role", role.name)
                }
            ) {
                filter {
                    eq("id", userId)
                }
            }

            onSuccess()
        }
    }
}