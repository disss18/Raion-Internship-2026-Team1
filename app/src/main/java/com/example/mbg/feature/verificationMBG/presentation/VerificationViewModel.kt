package com.example.mbg.verificationMBG

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mbg.core.supabase.SupabaseClientProvider
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.util.UUID

private const val TAG = "VERIFICATION_DEBUG"

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

            Log.d(TAG, "START VERIFICATION")

            try {

                val client = SupabaseClientProvider.client

                // ================= SESSION =================
                val session = client.auth.currentSessionOrNull()

                if (session == null) {
                    Log.e(TAG, "SESSION NULL - USER BELUM LOGIN")
                    return@launch
                }

                val userId = session.user?.id

                if (userId == null) {
                    Log.e(TAG, "USER ID NULL")
                    return@launch
                }

                Log.d(TAG, "USER ID = $userId")

                // ================= PROFILE =================
                val profile = client.from("profiles")
                    .select {
                        filter { eq("id", userId) }
                    }
                    .decodeSingleOrNull<ProfileResponse>()

                val userEmail = profile?.email ?: session.user?.email ?: ""

                Log.d(TAG, "USER EMAIL = $userEmail")

                // ================= VALIDASI FILE =================
                if (
                    fotoUsahaUri == null ||
                    fotoKtpUri == null ||
                    dokumenUri == null ||
                    buktiTransferUri == null
                ) {
                    Log.e(TAG, "FILE BELUM LENGKAP")
                    return@launch
                }

                Log.d(TAG, "SEMUA FILE ADA")

                // ================= EXTENSION =================
                val extUsaha = getFileExtension(context, fotoUsahaUri)
                val extKtp = getFileExtension(context, fotoKtpUri)
                val extDoc = getFileExtension(context, dokumenUri)
                val extTransfer = getFileExtension(context, buktiTransferUri)

                Log.d(TAG, "EXTENSION: usaha=$extUsaha ktp=$extKtp doc=$extDoc transfer=$extTransfer")

                // ================= UPLOAD STORAGE =================

                val usahaUrl = uploadFile(
                    context,
                    "$userId/usaha_${UUID.randomUUID()}.$extUsaha",
                    fotoUsahaUri
                )

                val ktpUrl = uploadFile(
                    context,
                    "$userId/ktp_${UUID.randomUUID()}.$extKtp",
                    fotoKtpUri
                )

                val dokumenUrl = uploadFile(
                    context,
                    "$userId/doc_${UUID.randomUUID()}.$extDoc",
                    dokumenUri
                )

                val transferUrl = uploadFile(
                    context,
                    "$userId/transfer_${UUID.randomUUID()}.$extTransfer",
                    buktiTransferUri
                )

                Log.d(TAG, "UPLOAD RESULT")
                Log.d(TAG, "usahaUrl = $usahaUrl")
                Log.d(TAG, "ktpUrl = $ktpUrl")
                Log.d(TAG, "dokumenUrl = $dokumenUrl")
                Log.d(TAG, "transferUrl = $transferUrl")

                if (
                    usahaUrl.isBlank() ||
                    ktpUrl.isBlank() ||
                    dokumenUrl.isBlank() ||
                    transferUrl.isBlank()
                ) {
                    Log.e(TAG, "UPLOAD GAGAL")
                    return@launch
                }

                // ================= INSERT DATABASE =================

                Log.d(TAG, "INSERT DATABASE")

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

                Log.d(TAG, "INSERT SUCCESS")

                onSuccess()

            } catch (e: Exception) {

                Log.e(TAG, "VERIFICATION ERROR", e)

            }
        }
    }

    // ================= STORAGE =================

    private suspend fun uploadFile(
        context: Context,
        fileName: String,
        uri: Uri
    ): String {

        return try {

            val inputStream = context.contentResolver.openInputStream(uri)
                ?: return ""

            val bytes = inputStream.readBytes()

            val bucket = SupabaseClientProvider.client
                .storage
                .from("berkas_dapur")

            bucket.upload(
                path = fileName,
                data = bytes
            )

            val publicUrl = bucket.publicUrl(fileName)

            Log.d(TAG, "UPLOAD SUCCESS: $publicUrl")

            publicUrl

        } catch (e: Exception) {

            Log.e(TAG, "UPLOAD ERROR", e)

            ""

        }
    }

    // ================= EXTENSION =================

    private fun getFileExtension(
        context: Context,
        uri: Uri?
    ): String {

        if (uri == null) return "jpg"

        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()

        return mimeTypeMap.getExtensionFromMimeType(
            contentResolver.getType(uri)
        ) ?: "jpg"
    }
}