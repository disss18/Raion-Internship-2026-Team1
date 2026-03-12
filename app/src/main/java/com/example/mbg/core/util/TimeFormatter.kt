package com.example.mbg.core.util

import java.time.Duration
import java.time.OffsetDateTime

fun formatTimeAgo(isoTime: String): String {

    return try {

        val time = OffsetDateTime.parse(isoTime)
        val now = OffsetDateTime.now()

        val diff = Duration.between(time, now)

        val minutes = diff.toMinutes()
        val hours = diff.toHours()
        val days = diff.toDays()

        when {
            minutes < 1 -> "Baru saja"
            minutes < 60 -> "$minutes menit lalu"
            hours < 24 -> "$hours jam lalu"
            else -> "$days hari lalu"
        }

    } catch (e: Exception) {
        "-"
    }
}