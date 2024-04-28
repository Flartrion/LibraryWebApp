package dataType

import kotlinx.serialization.Serializable

@Serializable
data class Rent(
    val id_rent: String,
    val id_user: String,
    val id_item: String,
    val from_date: String,
    val until_date: String,
    val id_storage: String
)
