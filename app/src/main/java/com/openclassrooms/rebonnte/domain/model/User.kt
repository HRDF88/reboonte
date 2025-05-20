package com.openclassrooms.rebonnte.domain.model

import java.io.Serializable

/**
 * Represents a user in the system.
 *
 * @property id The unique identifier of the user.
 * @property name The full name of the user.
 * @property email The email address of the user.
 *
 * Implements [Serializable] to allow the object to be serialized.
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
) : Serializable {
    constructor() : this("", "", "")
}
