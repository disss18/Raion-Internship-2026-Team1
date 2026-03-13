package com.example.mbg.feature.mbg.data

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.mbg.domain.model.MBGNeedModel
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