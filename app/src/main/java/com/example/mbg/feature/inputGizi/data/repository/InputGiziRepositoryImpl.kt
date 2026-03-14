package com.example.mbg.feature.inputGizi.data.repository

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.inputGizi.data.model.MenuItemInsert
import com.example.mbg.feature.inputGizi.domain.model.DailyPublishedMenu
import com.example.mbg.feature.inputGizi.domain.model.MenuItem
import com.example.mbg.feature.inputGizi.domain.repository.InputGiziRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.storage.storage
import java.io.File
import java.util.UUID

class InputGiziRepositoryImpl(
    private val supabase: SupabaseClient
) : InputGiziRepository {

    //Upload Foto ke Supabase Storage
    override suspend fun uploadFotoItem(file: File, fileName: String): String {
        val bucket = supabase.storage.from("menu_photos")
        val uniqueName = "${UUID.randomUUID()}_$fileName"

        bucket.upload(uniqueName, file.readBytes())
        return bucket.publicUrl(uniqueName)
    }

    //semua menu
    override suspend fun getMenuItems(): List<MenuItem> {
        return supabase.postgrest.from("menu_items")
            .select()
            .decodeList<MenuItem>()
    }

    //Simpan item menu baru ke database
    override suspend fun insertMenuItem(item: MenuItem) {

        val data = MenuItemInsert(
            id = item.id,
            dapur_id = item.dapur_id,
            nama_item = item.nama_item,
            foto_url = item.foto_url,
            berat_gram = item.berat_gram,
            kalori = item.kalori,
            karbohidrat = item.karbohidrat,
            protein = item.protein,
            lemak = item.lemak,
            created_at = item.created_at
        )

        supabase.postgrest
            .from("menu_items")
            .insert(data)
    }

    //Ambil menu spesifik per dapur (Katalog)
    override suspend fun getKatalogMenu(dapurId: String): List<MenuItem> {
        return supabase.postgrest.from("menu_items")
            .select {
                filter {
                    eq("dapur_id", dapurId)
                }
            }.decodeList<MenuItem>()
    }

    //Publish menu harian
    override suspend fun publishMenuHariIni(dailyMenu: DailyPublishedMenu) {
        supabase.postgrest.from("daily_published_menus").insert(dailyMenu)
    }
}