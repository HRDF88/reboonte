package com.openclassrooms.rebonnte.domain.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.openclassrooms.rebonnte.domain.model.Aisle
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import kotlin.test.*

class AisleExtensionsTest {

    @Test
    fun `toAisle returns Aisle when name is present`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.data } returns mapOf("name" to "testing")

        val result = snapshot.toAisle()

        assertNotNull(result)
        assertEquals("testing", result?.name)
    }

    @Test
    fun `toAisle returns null when snapshot data is null`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.data } returns null

        val result = snapshot.toAisle()

        assertNull(result)
    }

    @Test
    fun `toAisle returns Aisle with empty name when name is missing`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.data } returns mapOf("somethingElse" to "value")

        val result = snapshot.toAisle()

        assertNotNull(result)
        assertEquals("", result?.name)
    }

    @Test
    fun `toAisle returns Aisle with empty name when name is not a String`() {
        val snapshot = mockk<DocumentSnapshot>()
        every { snapshot.data } returns mapOf("name" to 123)

        val result = snapshot.toAisle()

        assertNotNull(result)
        assertEquals("", result?.name)
    }

    @Test
    fun `toFirestoreMap returns correct map for Aisle`() {
        val aisle = Aisle(name = "testing")

        val map = aisle.toFirestoreMap()

        assertEquals(1, map.size)
        assertEquals("testing", map["name"])
    }
}
