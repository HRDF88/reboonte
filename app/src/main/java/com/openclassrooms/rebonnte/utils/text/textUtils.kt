package com.openclassrooms.rebonnte.utils.text

/**
 * Utility object for formatting text related to aisles and medicines.
 */
object TextUtils {

    /**
     * Formats the given aisle name by converting it to lowercase.
     *
     * @param input The aisle name string to format.
     * @return The formatted aisle name in lowercase.
     */
    fun formatAisleName(input: String): String {
        return input.lowercase()
    }

    /**
     * Formats the given medicine name by converting it to lowercase.
     *
     * @param input The medicine name string to format.
     * @return The formatted medicine name in lowercase.
     */
    fun formatMedicineName(input: String): String {
        return input.lowercase()
    }
}