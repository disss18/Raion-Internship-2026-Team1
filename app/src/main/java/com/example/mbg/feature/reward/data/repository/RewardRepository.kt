package com.example.mbg.feature.reward.data.repository

import android.util.Log
import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.reward.data.dto.PointActivityDto
import com.example.mbg.feature.reward.data.dto.UserPointDto
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.postgresChangeFlow
import kotlinx.coroutines.flow.collect

class RewardRepository {

    private val supabase = SupabaseClientProvider.client
    private val TAG = "RewardRepository"

    /**
     * ENSURE USER POINT ROW EXISTS
     */
    suspend fun ensureUserPoint(userId: String) {

        try {

            val existing = supabase
                .from("user_points")
                .select {
                    filter { eq("user_id", userId) }
                }
                .decodeList<UserPointDto>()

            if (existing.isEmpty()) {

                Log.d(TAG, "CREATE NEW USER POINT ROW")

                supabase
                    .from("user_points")
                    .insert(
                        mapOf(
                            "user_id" to userId,
                            "total_point" to 0
                        )
                    )
            }

        } catch (e: Exception) {

            Log.e(TAG, "ENSURE USER POINT ERROR", e)
        }
    }


    /**
     * GET USER POINT
     */
    suspend fun getUserPoint(userId: String): Int {

        return try {

            ensureUserPoint(userId)

            Log.d(TAG, "GET USER POINT START userId=$userId")

            val result = supabase
                .from("user_points")
                .select {
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<UserPointDto>()

            val point = result.firstOrNull()?.total_point ?: 0

            Log.d(TAG, "POINT FROM DB = $point")

            point

        } catch (e: Exception) {

            Log.e(TAG, "GET USER POINT ERROR", e)

            0
        }
    }


    /**
     * REDEEM REWARD
     */
    suspend fun redeemReward(
        userId: String,
        rewardId: String,
        pointCost: Int
    ): String {

        val voucherCode = generateVoucherCode()

        try {

            ensureUserPoint(userId)

            val currentPoint = getUserPoint(userId)

            val newPoint = currentPoint - pointCost

            supabase
                .from("redeem_history")
                .insert(
                    mapOf(
                        "user_id" to userId,
                        "reward_id" to rewardId,
                        "voucher_code" to voucherCode
                    )
                )

            supabase
                .from("user_points")
                .update(
                    mapOf("total_point" to newPoint)
                ) {
                    filter {
                        eq("user_id", userId)
                    }
                }

            return voucherCode

        } catch (e: Exception) {

            Log.e(TAG, "REDEEM ERROR", e)

            return ""
        }
    }


    /**
     * REALTIME LISTENER
     */
    suspend fun listenUserPoint(
        userId: String,
        onPointUpdate: (Int) -> Unit
    ) {

        val channel = supabase.channel("user_points_channel")

        channel.subscribe()

        channel.postgresChangeFlow<PostgresAction.Update>(
            schema = "public"
        ) {
            table = "user_points"
        }.collect { change ->

            val changedUserId =
                change.record["user_id"]?.toString()

            if (changedUserId == userId) {

                val newPoint =
                    change.record["total_point"]
                        ?.toString()
                        ?.toIntOrNull() ?: 0

                onPointUpdate(newPoint)
            }
        }
    }


    /**
     * GENERATE VOUCHER CODE
     */
    private fun generateVoucherCode(): String {

        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }


    /**
     * ADD POINT (LIMIT 1X PER DAY)
     */
    suspend fun addPointIfNotToday(
        userId: String,
        activityType: String,
        point: Int
    ) {

        try {

            ensureUserPoint(userId)

            val today = java.text.SimpleDateFormat(
                "yyyy-MM-dd",
                java.util.Locale.getDefault()
            ).format(java.util.Date())

            val result = supabase
                .from("point_activity")
                .select {
                    filter {
                        eq("user_id", userId)
                        eq("activity_type", activityType)
                        gte("created_at", "$today 00:00:00")
                    }
                }
                .decodeList<PointActivityDto>()

            if (result.isNotEmpty()) {

                Log.d(TAG, "POINT ALREADY CLAIMED TODAY")

                return
            }

            supabase
                .from("point_activity")
                .insert(
                    PointActivityDto(
                        user_id = userId,
                        activity_type = activityType,
                        point = point
                    )
                )

            val currentPoint = getUserPoint(userId)

            val newPoint = currentPoint + point

            supabase
                .from("user_points")
                .update(
                    mapOf(
                        "total_point" to newPoint
                    )
                ) {
                    filter {
                        eq("user_id", userId)
                    }
                }

        } catch (e: Exception) {

            Log.e(TAG, "ADD POINT ERROR", e)
        }
    }
}