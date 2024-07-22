package dataType

import kotlinx.serialization.Serializable

@Serializable
data class Storage(
    val id: String,
    val address: String
)