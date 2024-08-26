package dataType

import kotlinx.serialization.Serializable

@Serializable
data class Rent(
    val id: String,
    val idUser: String,
    val idItem: String,
    val idStorage: String,
    val dateFrom: String,
    val dateUntil: String,
    val dateStatus: String,
    val status: String
)
