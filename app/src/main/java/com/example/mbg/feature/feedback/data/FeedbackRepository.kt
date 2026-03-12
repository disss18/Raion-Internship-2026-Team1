package com.example.mbg.feature.feedback.data

import com.example.mbg.core.supabase.SupabaseClientProvider
import com.example.mbg.feature.feedback.domain.model.FeedbackModel
import io.github.jan.supabase.postgrest.from
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

    /**
     * Hitung rating summary di Android
     * (bukan lewat SQL aggregate Supabase)
     */
    suspend fun getRatingSummary(
        feedbackList: List<FeedbackModel>
    ): Pair<Double, Map<Int, Int>> {

        if (feedbackList.isEmpty()) {
            return 0.0 to emptyMap()
        }

        /** AVERAGE RATING */
        val average =
            feedbackList
                .map { it.rating }
                .average()

        /** RATING DISTRIBUTION */
        val distribution =
            feedbackList
                .groupingBy { it.rating }
                .eachCount()

        println("SUMMARY RESULT: $distribution")

        return average to distribution
    }
}