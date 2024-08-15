package dataType

import kotlinx.serialization.Serializable

@Serializable
data class ItemLocation(
    val idItem: String,
    val idStorage: String,
    val amount: Int
)