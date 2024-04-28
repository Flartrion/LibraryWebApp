package dataType

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id_user: String,
    val role: Int,
    val full_name: String,
    val date_of_birth: String,
    val phone_number: String,
    val email: String
)