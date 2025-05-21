package com.openclassrooms.rebonnte.utils

import com.openclassrooms.rebonnte.utils.text.TextUtils
import kotlin.test.Test
import kotlin.test.assertEquals

class TextUtilsTest {

    @Test
    fun `formatAisleName converts uppercase to lowercase`() {
        val input = "Atari"
        val expected = "atari"

        val result = TextUtils.formatAisleName(input)

        assertEquals(expected, result)
    }

    @Test
    fun `formatAisleName keeps lowercase unchanged`() {
        val input = "atari"
        val expected = "atari"

        val result = TextUtils.formatAisleName(input)

        assertEquals(expected, result)
    }

    @Test
    fun `formatMedicineName keeps lowercase unchanged`() {
        val input = "atari"
        val expected = "atari"

        val result = TextUtils.formatMedicineName(input)

        assertEquals(expected, result)
    }
}
