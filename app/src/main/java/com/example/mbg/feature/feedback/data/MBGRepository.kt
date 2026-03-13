package com.example.mbg.feature.feedback.data

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.feedback.domain.model.MBGNeedModel
import io.github.jan.supabase.postgrest.from

class MBGRepository {

    private val supabase = SupabaseClientProvider.client

    suspend fun insertMBGNeed(
        need: MBGNeedModel
    ) {

        supabase
            .from("school_mbg_need")
            .insert(need)
    }
}