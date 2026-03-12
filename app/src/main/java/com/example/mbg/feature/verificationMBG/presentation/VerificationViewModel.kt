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
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ProfileResponse(
    val email: String? = null
)

class VerificationViewModel : ViewModel() {

    fun submitVerifikasi(
        context: Context,
        namaUmkm: String,
        alamat: String,
        namaPemilik: String,
        fotoUsahaUri: Uri?,
        fotoKtpUri: Uri?,
        dokumenUri: Uri?,
        buktiTransferUri: Uri?,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            try {
                val client = SupabaseClientProvider.client

                val session = client.auth.currentSessionOrNull()
                    ?: throw Exception("Session tidak ditemukan")

                val userId = session.user?.id ?: throw Exception("User ID tidak ditemukan")

                val profile = client.from("profiles")
                    .select {
                        filter {
                            eq("id", userId)
                        }
                    }.decodeSingleOrNull<ProfileResponse>()

                val userEmail = profile?.email ?: session.user?.email ?: ""

                val extUsaha = getFileExtension(context, fotoUsahaUri)
                val extKtp = getFileExtension(context, fotoKtpUri)
                val extDoc = getFileExtension(context, dokumenUri)
                val extTransfer = getFileExtension(context, buktiTransferUri)

                // 🔥 PERBAIKAN: Tambahin "$userId/" di depan nama file.
                // Supabase bakal otomatis bikinin folder pake ID KTP si Dapur!
                val usahaUrl = uploadFile(context, "$userId/usaha_${UUID.randomUUID()}.$extUsaha", fotoUsahaUri)
                val ktpUrl = uploadFile(context, "$userId/ktp_${UUID.randomUUID()}.$extKtp", fotoKtpUri)
                val dokumenUrl = uploadFile(context, "$userId/doc_${UUID.randomUUID()}.$extDoc", dokumenUri)
                val transferUrl = uploadFile(context, "$userId/transfer_${UUID.randomUUID()}.$extTransfer", buktiTransferUri)

                client.from("dapur_verifications").insert(
                    mapOf(
                        "nama_umkm" to namaUmkm,
                        "alamat" to alamat,
                        "nama_pemilik" to namaPemilik,
                        "foto_usaha_url" to usahaUrl,
                        "foto_ktp_url" to ktpUrl,
                        "dokumen_url" to dokumenUrl,
                        "bukti_transfer_url" to transferUrl,
                        "email" to userEmail,
                        "user_id" to userId,
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

    private fun getFileExtension(context: Context, uri: Uri?): String {
        if (uri == null) return "jpg"
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ?: "jpg"
    }
}