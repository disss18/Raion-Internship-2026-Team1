package com.example.mbg.feature.feedback.data

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.feedback.domain.model.FeedbackModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class FeedbackRepository {

    private val supabase = SupabaseClientProvider.client

    suspend fun getFeedback(
        limit: Long,
        offset: Long
    ): List<FeedbackModel> {

        val result =
            supabase
                .from("feedback")
                .select {

                    range(
                        from = offset,
                        to = offset + limit - 1
                    )

                    order(
                        column = "created_at",
                        order = Order.DESCENDING
                    )
                }
                .decodeList<FeedbackModel>()

        println("GET FEEDBACK RESULT SIZE = ${result.size}")
        println("GET FEEDBACK DATA = $result")

        return result
    }

    suspend fun insertFeedback(
        feedback: FeedbackModel
    ) {

        try {

            val result =
                supabase
                    .from("feedback")
                    .insert(feedback) {
                        select()
                    }

            println("INSERT SUCCESS: $result")

        } catch (e: Exception) {

            e.printStackTrace()

            println("INSERT ERROR: ${e.message}")

            throw e
        }
    }

    suspend fun getRatingSummary(): Pair<Double, Map<Int, Int>> {

        /** AVERAGE RATING */
        val averageResult =
            supabase
                .from("feedback")
                .select(Columns.raw("avg(rating)"))
                .decodeSingle<Map<String, Double?>>()

        val average =
            averageResult.values.firstOrNull() ?: 0.0


        /** RATING DISTRIBUTION */
        val distributionResult =
            supabase
                .from("feedback")
                .select(
                    Columns.raw(
                        "rating, count:count(*)"
                    )
                )
                .decodeList<Map<String, Int>>()

        val distribution =
            distributionResult.associate { row ->

                val star = row["rating"] ?: 0
                val count = row["count"] ?: 0

                star to count
            }

        println("SUMMARY RESULT: $distribution")

        return average to distribution
    }
}