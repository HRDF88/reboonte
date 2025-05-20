package com.openclassrooms.rebonnte.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility object for parsing and formatting date strings.
 *
 * Provides methods to parse raw date strings into timestamps
 * and format timestamps into localized date strings.
 */
object DateFormatter {

    private val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

    /**
     * Parses a raw date string into a timestamp (milliseconds since epoch).
     *
     * @param rawDate The raw date string to parse, expected in format "EEE MMM dd HH:mm:ss zzz yyyy".
     * @return The timestamp in milliseconds, or `null` if parsing fails.
     */
    fun parseToTimestamp(rawDate: String): Long? {
        return try {
            inputFormat.parse(rawDate)?.time
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Formats a timestamp (milliseconds since epoch) into a localized date string.
     *
     * The format changes depending on the device locale:
     * - For French ("fr"): "dd MMM yyyy 'à' HH'h'mm"
     * - For other locales: "MMM dd, yyyy 'at' HH:mm"
     *
     * @param timestamp The timestamp to format.
     * @return A localized date string representation of the timestamp.
     */
    fun formatToLocalizedDate(timestamp: Long): String {
        val locale = Locale.getDefault()
        val dateFormat = when (locale.language) {
            "fr" -> SimpleDateFormat("dd MMM yyyy 'à' HH'h'mm", locale)
            else -> SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", locale)
        }
        return dateFormat.format(Date(timestamp))
    }
}
