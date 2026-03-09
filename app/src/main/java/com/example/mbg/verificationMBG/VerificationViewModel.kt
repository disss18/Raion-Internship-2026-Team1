package com.example.mbg.verificationMBG

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.core.supabase.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import java.util.UUID

class VerificationViewModel : ViewModel() {

    fun submitVerifikasi(
        context: Context,
        namaUmkm: String,
        alamat: String,
        namaPemilik: String,
        fotoUsahaUri: Uri?,
        fotoKtpUri: Uri?,
        dokumenUri: Uri?,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                val client = SupabaseClientProvider.client

                // 1. Upload file ke bucket 'berkas_dapur'
                val usahaUrl = uploadFile(context, "usaha_${UUID.randomUUID()}", fotoUsahaUri)
                val ktpUrl = uploadFile(context, "ktp_${UUID.randomUUID()}", fotoKtpUri)
                val dokumenUrl = uploadFile(context, "doc_${UUID.randomUUID()}", dokumenUri)

                // 2. Insert ke tabel 'dapur_verifications'
                // Nama kolom di map ini WAJIB sama persis dengan di web Supabase tadi
                client.from("dapur_verifications").insert(
                    mapOf(
                        "nama_umkm" to namaUmkm,
                        "alamat" to alamat,
                        "nama_pemilik" to namaPemilik,
                        "foto_usaha_url" to usahaUrl,
                        "foto_ktp_url" to ktpUrl,
                        "dokumen_url" to dokumenUrl,
                        "status" to "pending"
                    )
                )

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun uploadFile(context: Context, fileName: String, uri: Uri?): String {
        if (uri == null) return ""
        try {
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes() ?: return ""
            val bucket = SupabaseClientProvider.client.storage.from("berkas_dapur")
            bucket.upload(fileName, bytes)
            return bucket.publicUrl(fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }
}