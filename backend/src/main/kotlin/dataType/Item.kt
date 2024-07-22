package dataType

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val id: String,
    val isbn: String,
    val rlbc: String,
    val title: String,
    val authors: String,
    val type: String,
    val details: String,
    val language: String,
)