package com.example.mbg.feature.feedback.data

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.feedback.domain.model.AllergyModel
import io.github.jan.supabase.postgrest.from

class AllergyRepository {

    private val supabase = SupabaseClientProvider.client

    suspend fun getAllergySummary(): List<AllergyModel> {

        return supabase
            .from("school_allergy")
            .select()
            .decodeList()
    }

    suspend fun insertAllergy(
        allergy: AllergyModel
    ) {

        supabase
            .from("school_allergy")
            .insert(allergy)
    }
}