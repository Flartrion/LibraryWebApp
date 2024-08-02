package dataType

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val role: String,
    val fullName: String,
    val dob: String?,
    val phoneNumber: String,
    val email: String
)