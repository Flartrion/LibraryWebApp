package dataType

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val idItem: String,
    val isbn: String,
    val rlbc: String,
    val title: String,
    val authors: String,
    val type: String,
    val details: String,
    val language: String,
)