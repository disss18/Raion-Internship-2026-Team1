package com.example.mbg.verificationMBG

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.core.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
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

                val session = client.auth.currentSessionOrNull()
                    ?: throw Exception("Session tidak ditemukan")

                val userId = session.user?.id

                val usahaUrl =
                    uploadFile(context, "usaha_${UUID.randomUUID()}", fotoUsahaUri)

                val ktpUrl =
                    uploadFile(context, "ktp_${UUID.randomUUID()}", fotoKtpUri)

                val dokumenUrl =
                    uploadFile(context, "doc_${UUID.randomUUID()}", dokumenUri)

                client.from("dapur_verifications").insert(

                    mapOf(
                        "user_id" to userId,
                        "nama_umkm" to namaUmkm,
                        "alamat" to alamat,
                        "nama_pemilik" to namaPemilik,
                        "foto_usaha_url" to usahaUrl,
                        "foto_ktp_url" to ktpUrl,
                        "dokumen_url" to dokumenUrl,
                        "status" to "pending"
                    )
                )

                println("VERIFICATION INSERT SUCCESS")

                onSuccess()

            } catch (e: Exception) {

                e.printStackTrace()

                println("VERIFICATION ERROR: ${e.message}")
            }
        }
    }

    private suspend fun uploadFile(
        context: Context,
        fileName: String,
        uri: Uri?
    ): String {

        if (uri == null) return ""

        return try {

            val bytes =
                context.contentResolver.openInputStream(uri)?.readBytes()
                    ?: return ""

            val bucket =
                SupabaseClientProvider.client.storage.from("berkas_dapur")

            bucket.upload(fileName, bytes)

            bucket.publicUrl(fileName)

        } catch (e: Exception) {

            e.printStackTrace()

            ""
        }
    }
}