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
     * GET USER POINT
     */
    suspend fun getUserPoint(userId: String): Int {

        return try {

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

            Log.d(TAG, "========================")
            Log.d(TAG, "START REDEEM")
            Log.d(TAG, "userId=$userId")
            Log.d(TAG, "rewardId=$rewardId")
            Log.d(TAG, "pointCost=$pointCost")
            Log.d(TAG, "voucherCode=$voucherCode")

            /**
             * GET CURRENT POINT
             */
            val currentPoint = getUserPoint(userId)

            Log.d(TAG, "CURRENT POINT = $currentPoint")

            val newPoint = currentPoint - pointCost

            Log.d(TAG, "NEW POINT = $newPoint")


            /**
             * INSERT HISTORY
             */
            Log.d(TAG, "INSERT REDEEM HISTORY")

            supabase
                .from("redeem_history")
                .insert(
                    mapOf(
                        "user_id" to userId,
                        "reward_id" to rewardId,
                        "voucher_code" to voucherCode
                    )
                )

            Log.d(TAG, "INSERT HISTORY SUCCESS")


            /**
             * UPDATE USER POINT
             */
            Log.d(TAG, "UPDATE USER POINT")

            supabase
                .from("user_points")
                .update(
                    mapOf("total_point" to newPoint)
                ) {
                    filter {
                        eq("user_id", userId)
                    }
                }

            Log.d(TAG, "POINT UPDATED SUCCESS")

            Log.d(TAG, "REDEEM SUCCESS")

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

        Log.d(TAG, "START REALTIME LISTENER")

        val channel = supabase.channel("user_points_channel")

        channel.subscribe()

        channel.postgresChangeFlow<PostgresAction.Update>(
            schema = "public"
        ) {

            table = "user_points"

        }.collect { change ->

            val changedUserId =
                change.record["user_id"]?.toString()

            Log.d(TAG, "REALTIME EVENT DETECTED")

            if (changedUserId == userId) {

                val newPoint =
                    change.record["total_point"]
                        ?.toString()
                        ?.toIntOrNull() ?: 0

                Log.d(TAG, "REALTIME POINT UPDATE = $newPoint")

                onPointUpdate(newPoint)
            }
        }
    }


    /**
     * GENERATE VOUCHER CODE
     */
    private fun generateVoucherCode(): String {

        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

        val code = (1..10)
            .map { chars.random() }
            .joinToString("")

        Log.d(TAG, "GENERATED VOUCHER CODE = $code")

        return code
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

            Log.d(TAG, "ADD POINT CHECK activity=$activityType")

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

            Log.d(TAG, "INSERT POINT ACTIVITY")

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

            Log.d(TAG, "ADD POINT newPoint=$newPoint")

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