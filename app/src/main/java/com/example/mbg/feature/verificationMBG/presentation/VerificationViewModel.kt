package com.example.mbg.verificationMBG

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
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
        buktiTransferUri: Uri?, // 🔥 Tambahan dari lu (Bukti Transfer)
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {
                val client = SupabaseClientProvider.client

                // Pengecekan Session (Kodingan Yudis)
                val session = client.auth.currentSessionOrNull()
                    ?: throw Exception("Session tidak ditemukan")

                // Ambil ID dan Email
                val userId = session.user?.id
                val userEmail = session.user?.email ?: "" // 🔥 Ambil email buat notif Resend

                // 🔥 Pasang pendeteksi ekstensi biar file di Supabase bisa dibuka (.jpg / .pdf)
                val extUsaha = getFileExtension(context, fotoUsahaUri)
                val extKtp = getFileExtension(context, fotoKtpUri)
                val extDoc = getFileExtension(context, dokumenUri)
                val extTransfer = getFileExtension(context, buktiTransferUri)

                val usahaUrl = uploadFile(context, "usaha_${UUID.randomUUID()}.$extUsaha", fotoUsahaUri)
                val ktpUrl = uploadFile(context, "ktp_${UUID.randomUUID()}.$extKtp", fotoKtpUri)
                val dokumenUrl = uploadFile(context, "doc_${UUID.randomUUID()}.$extDoc", dokumenUri)
                val transferUrl = uploadFile(context, "transfer_${UUID.randomUUID()}.$extTransfer", buktiTransferUri) // 🔥 Upload Bukti Transfer

                client.from("dapur_verifications").insert(
                    mapOf(
                        "nama_umkm" to namaUmkm,
                        "alamat" to alamat,
                        "nama_pemilik" to namaPemilik,
                        "foto_usaha_url" to usahaUrl,
                        "foto_ktp_url" to ktpUrl,
                        "dokumen_url" to dokumenUrl,
                        "bukti_transfer_url" to transferUrl, // 🔥 Masukin ke database
                        "email" to userEmail,                // 🔥 Masukin ke database
                        "user_id" to userId,                 // Masukin ke database (Ide Yudis)
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
            val bytes = context.contentResolver.openInputStream(uri)?.readBytes()
                ?: return ""
            val bucket = SupabaseClientProvider.client.storage.from("berkas_dapur")
            bucket.upload(fileName, bytes)
            bucket.publicUrl(fileName)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // 🔥 Fungsi ini JANGAN dihapus, ini biar file nggak corrupt di Supabase
    private fun getFileExtension(context: Context, uri: Uri?): String {
        if (uri == null) return "jpg"
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"
    }
}