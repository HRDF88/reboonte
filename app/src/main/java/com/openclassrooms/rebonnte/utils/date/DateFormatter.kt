package com.openclassrooms.rebonnte.utils.date

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatter {

    private val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

    fun parseToTimestamp(rawDate: String): Long? {
        return try {
            inputFormat.parse(rawDate)?.time
        } catch (e: Exception) {
            null
        }
    }
    fun formatToLocalizedDate(timestamp: Long): String {
        val locale = Locale.getDefault()
        val dateFormat = when (locale.language) {
            "fr" -> SimpleDateFormat("dd MMM yyyy 'à' HH'h'mm", locale)
            else -> SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", locale)
        }
        return dateFormat.format(Date(timestamp))
    }
}
