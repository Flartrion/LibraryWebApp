package dataType

import kotlinx.serialization.Serializable

@Serializable
data class BankHistoryEntry(
    val id: String,
    val idItem: String,
    val idStorage: String,
    val change: String,
    val date: String
)