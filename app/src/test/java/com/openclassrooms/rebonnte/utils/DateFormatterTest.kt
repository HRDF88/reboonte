package com.openclassrooms.rebonnte.utils

import com.openclassrooms.rebonnte.utils.date.DateFormatter
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DateFormatterTest {

    @Test
    fun `parseToTimestamp returns correct timestamp for valid date string`() {
        val rawDate = "Wed May 20 14:30:00 GMT 2020"
        val timestamp = DateFormatter.parseToTimestamp(rawDate)

        val expected = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(rawDate)?.time

        assertEquals(expected, timestamp)
    }

    @Test
    fun `parseToTimestamp returns null for invalid date string`() {
        val invalidDate = "invalid date string"
        val timestamp = DateFormatter.parseToTimestamp(invalidDate)

        assertNull(timestamp)
    }

    @Test
    fun `formatToLocalizedDate returns french formatted date when locale is fr`() {
        val timestamp = 1589980200000L // corresponds to Wed May 20 14:30:00 GMT 2020

        val defaultLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.FRENCH)

            val formatted = DateFormatter.formatToLocalizedDate(timestamp)
            assert(formatted.contains("à"))
            assert(formatted.matches(Regex("""\d{2} \p{L}{3,} \d{4} à \d{2}h\d{2}""")))
        } finally {
            Locale.setDefault(defaultLocale)
        }
    }

    @Test
    fun `formatToLocalizedDate returns english formatted date when locale is not fr`() {
        val timestamp = 1589980200000L // corresponds to Wed May 20 14:30:00 GMT 2020

        val defaultLocale = Locale.getDefault()
        try {
            Locale.setDefault(Locale.ENGLISH)

            val formatted = DateFormatter.formatToLocalizedDate(timestamp)

            assert(formatted.contains("at"))
            assert(formatted.matches(Regex("""\p{L}{3} \d{2}, \d{4} at \d{2}:\d{2}""")))
        } finally {
            Locale.setDefault(defaultLocale)
        }
    }
}
